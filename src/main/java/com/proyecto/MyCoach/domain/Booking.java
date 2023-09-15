package com.proyecto.MyCoach.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity (name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "booking_date")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate bookingDate;
    @Column (name = "start_time")
    private LocalTime startTime;
    @Column
    private float duration;
    @Column
    private boolean tools;
    @Column
    private boolean paid;


    @ManyToOne
    @JoinColumn (name = "user_id")
    @JsonBackReference ("value = user-booking")
    private User user;

    @ManyToOne
    @JoinColumn (name = "trainer_id")
    @JsonBackReference ("value = trainer-booking")
    private Trainer trainer;

    @ManyToOne
    @JoinColumn (name = "phisiotherapist_id")
    @JsonBackReference ("value = phisiotherapist-booking")
    private Phisiotherapist phisiotherapist;




}