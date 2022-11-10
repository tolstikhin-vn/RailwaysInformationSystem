package ru.tolstikhin.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "cities")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id")
    private int city_id;

//    @OneToMany(mappedBy = "city")
//    private List<TrainStation> trainStation;

    @Column(name = "city_name")
    private String city_name;

    public int getCityId() {
        return city_id;
    }

    public String getCityName() {
        return city_name;
    }
}
