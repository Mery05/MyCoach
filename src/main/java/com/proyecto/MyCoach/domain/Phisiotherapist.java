package com.proyecto.MyCoach.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity (name = "phisiotherapist")
public class Phisiotherapist {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String name;
    @Column
    private boolean available;

}
