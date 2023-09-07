package com.proyecto.MyCoach.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity (name = "trainer")
public class Trainer {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    @NotNull
    @NotBlank
    private String name;
    @Column
    @NotNull
    private String surname;
    @Column (name = "hiring_date")
    @JsonFormat (pattern = "dd-MM-yyyy")
    private LocalDate hiringDate;
    @Column
    private boolean available;
    @Column
    @Min(value = 0)
    private float price;

    @OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL)
    private List<Booking> bookingsTrainer;

    @ManyToOne
    @JoinColumn (name = "headquarter_id")
    @JsonBackReference("value = headquarter-trainer")
    private Headquarter headquarter;
}
