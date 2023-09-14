package com.proyecto.MyCoach.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity (name = "headquarter")
public class Headquarter {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    @NotNull
    @NotBlank
    private String name;
    @Column
    @NotNull
    private String city;
    @Column
    private String address;
    @Column (name = "enrollment_number")
    private int enrollmentNumber;
    @Column (name = "class_number")
    @Min(value = 0)
    private int classNumber;

    @OneToMany(mappedBy = "headquarter", cascade = CascadeType.ALL)
    private List<Trainer> trainers;

    @OneToMany(mappedBy = "headquarter", cascade = CascadeType.ALL)
    private List<Phisiotherapist> phisiotherapists;


}
