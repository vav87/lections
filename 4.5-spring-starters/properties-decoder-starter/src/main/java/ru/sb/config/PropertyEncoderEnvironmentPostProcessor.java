package ru.sb.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.util.Base64Utils.decodeFromString;

public class PropertyEncoderEnvironmentPostProcessor
        implements EnvironmentPostProcessor {
    private static final String ENCODING_PREFIX = "base64:";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        final Map<String, Object> map = new HashMap<>();
        for(final PropertySource<?> propertySource : environment.getPropertySources()) {
            if (propertySource instanceof EnumerablePropertySource) {
                for (String key : ((EnumerablePropertySource) propertySource).getPropertyNames()) {
                    final String value = propertySource.getProperty(key).toString();
                    if (value.startsWith(ENCODING_PREFIX)) {
                        final String encodedValue = value.substring(7);
                        final String decodedValue = new String(decodeFromString(encodedValue));
                        map.put(key, decodedValue);
                    }
                }
            }
        }
        environment.getPropertySources().addFirst(new MapPropertySource("custom", map));
    }
}
