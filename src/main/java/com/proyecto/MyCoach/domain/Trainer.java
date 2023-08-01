package com.proyecto.MyCoach.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity (name = "trainer")
public class Trainer {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String name;
    @Column
    private boolean available;
}
