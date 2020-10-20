package ru.digitalhabbits.dbconnectorhibernate.dao;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;
import ru.digitalhabbits.dbconnectorhibernate.model.User;
import ru.digitalhabbits.dbconnectorhibernate.utils.Context;


@RequiredArgsConstructor
@Component("UserDaoWithHibernate")
public class UserDaoWithSession implements Crud<Context<User>> {

    private final SessionFactory sessionFactory;

    @Override
    public Context<User> create(Context<User> context) {
        // Можно так
        // Query query = sessionFactory.createEntityManager().createQuery(String.format("INSERT INTO users (name) VALUES %s", ctx.entity().getName()));
        // int result = query.executeUpdate();

        // Можно так
        // User user = sessionFactory.createEntityManager().find(User.class, ctx.entity().getId());

        // Можно так
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(context.entity());
        transaction.commit();
        session.close();
        return context;
    }

    @Override
    public Context<User> read(Context<User> context) {
        // Получаем объект с помощью API Hibernate
        User user = sessionFactory.openSession().get(User.class, context.entity().getId());

        var id = user.getId();
        var text = user.getName();
        return new Context<>(new User(id, text));
    }

    @Override
    public Context<User> update(Context<User> context) {
        return context;
    }

    @Override
    public Context<User> delete(Context<User> context) {
        return context;
    }

    public Context<User> readAllMessages(Context<User> context) {
        User user = sessionFactory.openSession().get(User.class, context.entity().getId());

        var id = user.getId();
        var text = user.getName();
        return new Context<>(new User(id, text));
    }
    
}
