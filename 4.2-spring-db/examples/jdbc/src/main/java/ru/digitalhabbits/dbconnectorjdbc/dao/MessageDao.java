package ru.digitalhabbits.dbconnectorjdbc.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.digitalhabbits.dbconnectorjdbc.model.Message;
import ru.digitalhabbits.dbconnectorjdbc.utils.Context;

import java.sql.*;

import static ru.digitalhabbits.dbconnectorjdbc.utils.Status.ERROR;
import static ru.digitalhabbits.dbconnectorjdbc.utils.Status.NOT_FOUND;

@RequiredArgsConstructor
@Component
public class MessageDao implements Crud<Context<Message>> {

    private static String INSERT = "INSERT INTO messages (text) VALUES (?)";
    private static String SELECT = "SELECT * FROM messages WHERE id=(?)";

    @Override
    public Context<Message> create(Context<Message> ctx) {
        try {
            // Создаем соединение с базой данных
            Connection connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/messages",
                    "messages",
                    "messages");

            // Можно реализовать и так
            // Statement statement = connection.createStatement();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    INSERT,
                    Statement.RETURN_GENERATED_KEYS
            );

            // Задаем значение для запроса INSERT INTO messages (text) VALUES (?)
            preparedStatement.setString(1, ctx.entity().text());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                return Context.<Message>builder()
                        .status(ERROR)
                        .problem("Не смогли вставить объект в базу данных")
                        .build();
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {

                if (!generatedKeys.next()) {
                    return Context.<Message>builder()
                            .status(ERROR)
                            .problem("Не смогли получить ID из базы данных")
                            .build();
                }

                return new Context<>(new Message(generatedKeys.getLong(1), ctx.entity().text()));
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
            return Context.<Message>builder()
                    .status(ERROR)
                    .problem("Произошла ошибка в запросе к базе данных")
                    .build();
        }
    }

    @Override
    public Context<Message> read(Context<Message> context) {
        try {
            // Создаем соединение с базой данных
            // Bad Practice
            Connection connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/messages",
                    "messages",
                    "messages");

            // Можно реализовать так
            // Statement statement = connection.createStatement();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    SELECT,
                    Statement.RETURN_GENERATED_KEYS
            );

            // Задаем значение для запроса INSERT INTO messages (text) VALUES (?)
            preparedStatement.setLong(1, context.entity().id());

            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                return Context.<Message>builder()
                        .status(NOT_FOUND)
                        .problem("Объект не найден в базе данных")
                        .build();
            }

            var id = resultSet.getLong("id");
            var text = resultSet.getString("text");
            return new Context<>(new Message(id, text));

        } catch (SQLException exception) {
            exception.printStackTrace();
            return Context.<Message>builder()
                    .status(ERROR)
                    .problem("Произошла ошибка в запросе к базе данных")
                    .build();
        }
    }

    @Override
    public Context<Message> update(Context<Message> context) {
        return context;
    }

    @Override
    public Context<Message> delete(Context<Message> context) {
        return context;
    }

}
