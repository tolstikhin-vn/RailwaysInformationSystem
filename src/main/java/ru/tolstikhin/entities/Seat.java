package ru.tolstikhin.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    @Column(name = "wagon_number")
    private int wagon;

    @Column(name = "seat_price")
    private float seat_price;

    public int getSeatNumberInWagon() {
        return seat_number_in_wagon;
    }

    public float getSeatPrice() {
        return seat_price;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    public int getSeatNumber() {
        return seat_number;
    }
}
