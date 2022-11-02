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
@Table(name = "trains")
public class Train {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "train_number")
    private int train_number;

    @OneToMany(mappedBy = "train")
    private List<Route> routes;

    @OneToMany(mappedBy = "train")
    private List<Wagon> wagons;

    @Column(name = "train_name")
    private String train_name;
}
