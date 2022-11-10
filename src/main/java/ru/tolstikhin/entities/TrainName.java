package ru.tolstikhin.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "train_names")
public class TrainName {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "train_name_id")
    private int train_name_id;

    @Column(name = "train_name")
    private String train_name;
}
