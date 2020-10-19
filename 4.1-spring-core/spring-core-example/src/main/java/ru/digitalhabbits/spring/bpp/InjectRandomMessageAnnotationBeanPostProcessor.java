package ru.digitalhabbits.spring.bpp;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import ru.digitalhabbits.spring.annotations.InjectRandomMessage;

import java.lang.reflect.Field;

import static ru.digitalhabbits.spring.utils.RandomUtils.generateRandomString;

@Component
public class InjectRandomMessageAnnotationBeanPostProcessor
        implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Field[] declaredFields = bean.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            InjectRandomMessage annotation = field.getAnnotation(InjectRandomMessage.class);
            if (annotation != null) {
                String message = generateRandomString(annotation.length());
                field.setAccessible(true);
                ReflectionUtils.setField(field, bean, message);
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
