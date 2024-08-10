package org.example.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long userId;

    @Column(name = "user_name", nullable = false)
    private String userName;


    @Column(name = "age", nullable = false)
    private int age;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    List<Note> notes;


}


