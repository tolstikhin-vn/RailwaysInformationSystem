package ru.tolstikhin.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.sql.Time;
import java.sql.Date;

@Entity
@Table(name = "routs")
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rout_id")
    private int rout_id;

    @ManyToOne
    @JoinColumn(name = "train")
    private Train train;

    @ManyToOne
    @JoinColumn(name = "station_from")
    private TrainStation trainStationFrom;

    @ManyToOne
    @JoinColumn(name = "station_to")
    private TrainStation trainStationTo;

    @Column(name = "departure_date")
    private Date departure_date;

    @Column(name = "arrival_date")
    private Date arrival_date;

    @Column(name = "arrival_time")
    private Time arrival_time;

    @Column(name = "departure_time")
    private Time departure_time;
}
