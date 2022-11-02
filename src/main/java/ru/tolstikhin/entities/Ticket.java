package ru.tolstikhin.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_number")
    private int ticket_number;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "train_name")
    private String train_name;

    @Column(name = "wagon_number")
    private int wagon_number;

    @Column(name = "wagon_type")
    private String wagon_type;

    @Column(name = "seat_number")
    private int seat_number;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "passport_data")
    private String passport_data;

    @Column(name = "birthdate")
    private Date birthdate;

    @Column(name = "departure_date")
    private Date departure_date;

    @Column(name = "arrival_date")
    private Date arrival_date;

    @Column(name = "departure_time")
    private Time departure_time;

    @Column(name = "arrival_time")
    private Time arrival_time;

    @Column(name = "city_from")
    private String city_from;

    @Column(name = "city_to")
    private String city_to;
}
