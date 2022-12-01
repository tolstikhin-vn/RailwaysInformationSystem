package ru.tolstikhin.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;;
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

    @Column(name = "wagon_type")
    private int wagon_type;

    @Column(name = "train")
    private int train;

    @OneToMany(mappedBy = "wagon")
    private List<Seat> seats;

    public int getWagonNumber() {
        return wagon_number;
    }

    public int getWagonNumberOnTrain() {
        return wagon_number_on_train;
    }

    public int getWagonType() {
        return wagon_type;
    }
}
