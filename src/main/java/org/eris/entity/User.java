package org.eris.entity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity // required for psql to create the entity class
@Table(name = "users")// creates the table of users in database
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false) // strict schema with constraints enforced at database level
    private String userName;

    @Column(nullable = false)
    private String password;
    private List<String> roles;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Note> notes;
}
