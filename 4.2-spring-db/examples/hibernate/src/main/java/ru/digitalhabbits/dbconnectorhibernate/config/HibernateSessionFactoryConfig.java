package ru.digitalhabbits.dbconnectorhibernate.config;


import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.digitalhabbits.dbconnectorhibernate.model.Message;
import ru.digitalhabbits.dbconnectorhibernate.model.User;

@Configuration
public class HibernateSessionFactoryConfig {

    @Bean()
    public SessionFactory sessionFactory() {
        SessionFactory sessionFactory;
        org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration().configure();
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Message.class);
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties());
        sessionFactory = configuration.buildSessionFactory(builder.build());
        return sessionFactory;
    }

}
