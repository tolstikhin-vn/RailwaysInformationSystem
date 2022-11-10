package ru.tolstikhin.entities;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction")
    private int transaction_id;

    @Column(name = "user_id")
    private int user_id;

    @Column(name = "transaction_date")
    private LocalDateTime transaction_date;
}
