package ru.tolstikhin.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "routes")
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "route_id")
    private int route_id;

    @Column(name = "station_from")
    private int station_from;

    @Column(name = "station_to")
    private int station_to;

    public int getRouteId() {
        return route_id;
    }


    public int getStationFrom() {
        return station_from;
    }

    public int getStationTo() {
        return station_to;
    }
}
