package ru.digitalhabbits.spring.bpp;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import ru.digitalhabbits.spring.annotations.Timed;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Component
public class TimedAnnotationBeanPostProcessor
        implements BeanPostProcessor {

    private Map<String, Class<?>> map = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        if (beanClass.isAnnotationPresent(Timed.class)) {
            map.put(beanName, beanClass);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = map.get(beanName);
        if (beanClass != null) {
            return Proxy.newProxyInstance(
                    beanClass.getClassLoader(),
                    beanClass.getInterfaces(),
                    new InvocationHandler() {
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            System.out.print(Instant.now() + " ");
                            return method.invoke(bean, args);
                        }
                    }
            );
        }
        return bean;
    }
}
