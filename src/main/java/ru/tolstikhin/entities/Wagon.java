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
@Table(name = "wagons")
public class Wagon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wagon_number")
    private int wagon_number;

    @Column(name = "wagon_number_on_train")
    private int wagon_number_on_train;

    @ManyToOne
    @JoinColumn(name = "wagon_type")
    private WagonType wagon_type;

    @ManyToOne
    @JoinColumn(name = "train")
    private Train train;

    @OneToMany(mappedBy = "wagon")
    private List<Seat> seats;
}
