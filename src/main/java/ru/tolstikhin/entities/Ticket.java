package ru.tolstikhin.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    @Column(name = "user_id")
    private int user_id;

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

    @Column(name = "price")
    private double price;

    public Ticket() {
    }
    public Ticket(int user_id, String train_name, int wagon_number, String wagon_type, int seat_number, String name,
                  String surname, String patronymic, String passport_data, Date birthdate, Date departure_date,
                  Time departure_time, Date arrival_date, Time arrival_time, String city_from, String city_to,
                  double price) {
        this.user_id = user_id;
        this.train_name = train_name;
        this.wagon_number = wagon_number;
        this.wagon_type = wagon_type;
        this.seat_number = seat_number;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.passport_data = passport_data;
        this.birthdate = birthdate;
        this.departure_date = departure_date;
        this.departure_time = departure_time;
        this.arrival_date = arrival_date;
        this.arrival_time = arrival_time;
        this.city_from = city_from;
        this.city_to = city_to;
        this.price = price;
    }
}
