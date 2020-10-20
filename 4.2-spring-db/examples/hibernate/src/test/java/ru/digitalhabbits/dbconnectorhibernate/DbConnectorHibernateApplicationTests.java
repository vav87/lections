package ru.digitalhabbits.dbconnectorhibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.digitalhabbits.dbconnectorhibernate.model.Message;
import ru.digitalhabbits.dbconnectorhibernate.model.User;

import java.util.List;

@SpringBootTest
class DbConnectorHibernateApplicationTests {

    @Test
    void contextLoads() {

    }

    @BeforeAll
    private static void createEntitites() {
        SessionFactory sessionFactory;
        org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration().configure();
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Message.class);
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties());
        sessionFactory = configuration.buildSessionFactory(builder.build());

        Session session = sessionFactory.openSession();
        for (int i = 0; i < 5; i++) {
            
            User user = new User("name" + i);
            Message message = new Message("message" + i);
            message.setUser(user);
            session.persist(message);
            session.persist(user);
            
        }
        session.close();
    }

    @Test
    @DisplayName("если fetch = FetchType.EAGER, то получаем в консоли N+1 select: 5+1")
    public void whenEager_thenNplus1Problem() {
        SessionFactory sessionFactory;
        org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration().configure();
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Message.class);
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties());
        sessionFactory = configuration.buildSessionFactory(builder.build());
        Session session = sessionFactory.openSession();

        Query<Message> query = session.createQuery("SELECT msg FROM Message msg");
        List<Message> comments = query.getResultList();
        Assertions.assertEquals(5, comments.size());
        comments.forEach(comment -> System.out.println(comment.getText() + " " + comment.getUser().getName()));
    }

}
