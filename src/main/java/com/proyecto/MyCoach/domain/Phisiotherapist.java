package com.proyecto.MyCoach.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

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
    private String surname;
    @Column (name = "hiring_date")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate hiringDate;
    @Column
    private boolean available;
    @Column
    private float price;

    @OneToMany(mappedBy = "phisiotherapist", cascade = CascadeType.ALL)
    private List<Booking> bookingsPhisio;

    @ManyToOne
    @JoinColumn (name = "headquarter_id")
    @JsonBackReference("value = headquarter-phiotherapist")
    private Headquarter headquarter;

}
