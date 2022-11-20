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
@Table(name = "wagons_types")
public class WagonType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_id")
    private int type_id;

    @OneToMany(mappedBy = "wagon_type")
    private List<Wagon> wagons;

    @Column(name = "type_name")
    private String type_name;

    public String getTypeName() {
        return type_name;
    }
}
