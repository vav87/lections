package ru.digitalhabbits.dbconnectorhibernate.dao;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;
import ru.digitalhabbits.dbconnectorhibernate.model.Message;
import ru.digitalhabbits.dbconnectorhibernate.model.User;
import ru.digitalhabbits.dbconnectorhibernate.utils.Context;


@RequiredArgsConstructor
@Component("MessageDao")
public class MessageDaoWithSession {

    private final SessionFactory sessionFactory;

    public User createMessageWithUserId(long userId, String text) {

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        User user = session.get(User.class, userId);
        user.getMessages().add(Message.with(user, text));
        session.persist(user);
        session.flush();
        
        // Работа с откатом транзакции rollback()
//        try {
//            throw new Exception();
//        } catch (Exception ex) {
//            transaction.rollback();
//        }

        transaction.commit();
        session.close();
        return user;
    }

    public Context<Message> read(Context<Message> context) {
        // Получаем объект с помощью API Hibernate
        User user = sessionFactory.openSession().get(User.class, context.entity().getId());

        var id = user.getId();
        var text = user.getName();
        return new Context<>(new Message(id, text));
    }

}
