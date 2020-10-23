package ru.digitalhabbits.sb;

import org.slf4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import static org.slf4j.LoggerFactory.getLogger;

public class DeprecatedClassBeanFactoryPostProcessor
        implements BeanFactoryPostProcessor {
    private static final Logger logger = getLogger(DeprecatedClassBeanFactoryPostProcessor.class);

    /*
     * Если используется создание bean через factory-method, то на этапе BeanFactoryPostProcessor
     * нет информации о типе bean'а, т.е. beanType == null, зато есть factoryMethod.
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
            throws BeansException {
        for (String beanName : beanFactory.getBeanDefinitionNames()) {
            final BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
            Class<?> beanType = beanFactory.getType(beanName);
            logger.info("{} {}", beanDefinition, beanType);
            if (beanType.isAnnotationPresent(DeprecatedClass.class)) {
                final DeprecatedClass annotation = beanType.getAnnotation(DeprecatedClass.class);
                beanDefinition.setBeanClassName(annotation.newImpl().getName());
            }
        }
    }
}
