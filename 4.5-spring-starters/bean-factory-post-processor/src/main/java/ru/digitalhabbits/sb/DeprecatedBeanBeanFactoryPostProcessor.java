package ru.digitalhabbits.sb;

import org.slf4j.Logger;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;

import static org.slf4j.LoggerFactory.getLogger;

public class DeprecatedBeanBeanFactoryPostProcessor
        implements BeanFactoryPostProcessor,
                   EnvironmentAware {
    private static final Logger logger = getLogger(DeprecatedBeanBeanFactoryPostProcessor.class);
    private static final String PROD_ENV = "prod";

    private Environment environment;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        if (!environment.acceptsProfiles(Profiles.of(PROD_ENV))) {
            for (String beanName : beanFactory.getBeanDefinitionNames()) {
                Class<?> beanType = beanFactory.getType(beanName);
                if (beanType.isAnnotationPresent(Deprecated.class)) {
                    logger.warn("Bean '{}' is marked as deprecated", beanName);
                }
            }
        }
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
