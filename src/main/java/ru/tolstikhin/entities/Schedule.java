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

    public void setScheduleId(int schedule_id) {
        this.schedule_id = schedule_id;
    }

    public int getRouteId() {
        return route_id;
    }

    public void setRouteId(int route_id) {
        this.route_id = route_id;
    }

    public int getTrainNumber() {
        return train_number;
    }

    public void setTrainNumber(int train_number) {
        this.train_number = train_number;
    }

    public Date getDepartureDate() {
        return departure_date;
    }

    public void setDepartureDate(Date departure_date) {
        this.departure_date = departure_date;
    }

    public Time getDepartureTime() {
        return departure_time;
    }

    public void setDepartureTime(Time departure_time) {
        this.departure_time = departure_time;
    }

    public Date getArrivalDate() {
        return arrival_date;
    }

    public void setArrivalDate(Date arrival_date) {
        this.arrival_date = arrival_date;
    }

    public Time getArrivalTime() {
        return arrival_time;
    }

    public void setArrivalTime(Time arrival_time) {
        this.arrival_time = arrival_time;
    }
}
