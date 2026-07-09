package org.eris.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "notes")
@Data
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(nullable = false)
    public String title;

    public String content;

    public LocalDateTime createdAt; // -> extended the localtime feature of spring
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

}
