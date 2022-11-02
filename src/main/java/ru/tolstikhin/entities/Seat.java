package ru.tolstikhin.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "seats")
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_number")
    private int seat_number;

    @Column(name = "seat_number_in_wagon")
    private int seat_number_in_wagon;

    @Column(name = "booked")
    private boolean booked;

    @ManyToOne
    @JoinColumn(name = "wagon_number")
    private Wagon wagon;

    @Column(name = "seat_price")
    private int seat_price;
}
