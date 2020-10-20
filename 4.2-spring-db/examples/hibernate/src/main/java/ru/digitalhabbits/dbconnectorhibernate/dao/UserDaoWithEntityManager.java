package ru.digitalhabbits.dbconnectorhibernate.dao;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import ru.digitalhabbits.dbconnectorhibernate.model.User;
import ru.digitalhabbits.dbconnectorhibernate.utils.Context;

import javax.persistence.EntityManager;


@RequiredArgsConstructor
@Component("UserDaoWithEntityManager")
public class UserDaoWithEntityManager implements Crud<Context<User>> {

    private final SessionFactory sessionFactory;

    @Override
    public Context<User> create(Context<User> context) {
        // Можно так
        // Query query = sessionFactory.createEntityManager().createQuery(String.format("INSERT INTO users (name) VALUES %s", ctx.entity().getName()));
        // int result = query.executeUpdate();

        // Можно так
        sessionFactory.createEntityManager().persist(context.entity());

        return context;
    }

    @Override
    public Context<User> read(Context<User> context) {
        // Получаем объект с помощью API Hibernate
        User user = sessionFactory.createEntityManager().find(User.class, context.entity().getId());

        var id = user.getId();
        var name = user.getName();
        return new Context<>(new User(id, name));
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
        return null;
    }
}
