package ru.digitalhabbits.dbconnectorhibernate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "text")
    private String text;

    @ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.PERSIST)
    @JoinColumn(name="user_id")
    private User user;

    public Message(long id) {
        this.id = id;
    }

    public Message(User user, String text) {
        this.user = user;
        this.text = text;
    }

    public Message(String text) {
        this.text = text;
    }
    
    public Message(long id, String text) {
        this.id = id;
        this.text = text;
    }

    public static Message with(long id) {
        return new Message(id);
    }

    public static Message with(User user, String text) {
        return new Message(user, text);
    }

}
