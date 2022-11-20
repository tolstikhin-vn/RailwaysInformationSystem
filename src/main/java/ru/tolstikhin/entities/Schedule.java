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
@Table(name = "schedules")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private int schedule_id;

    @Column(name = "route_id")
    private int route_id;

    @Column(name = "train_number")
    private int train_number;

    @Column(name = "departure_date")
    private Date departure_date;

    @Column(name = "departure_time")
    private Time departure_time;

    @Column(name = "arrival_date")
    private Date arrival_date;

    @Column(name = "arrival_time")
    private Time arrival_time;

    public int getScheduleId() {
        return schedule_id;
    }

    public int getTrainNumber() {
        return train_number;
    }

    public Date getDepartureDate() {
        return departure_date;
    }

    public Time getDepartureTime() {
        return departure_time;
    }

    public Date getArrivalDate() {
        return arrival_date;
    }

    public Time getArrivalTime() {
        return arrival_time;
    }
}
