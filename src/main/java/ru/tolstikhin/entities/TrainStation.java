package ru.tolstikhin.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "train_stations")
public class TrainStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "station_id")
    private int station_id;

    @OneToMany(mappedBy = "trainStationFrom")
    private List<Route> routes1;

    @OneToMany(mappedBy = "trainStationTo")
    private List<Route> routes2;

    @Column(name = "station_name")
    private String station_name;

    @ManyToOne
    @JoinColumn(name = "city")
    private City city;
}
