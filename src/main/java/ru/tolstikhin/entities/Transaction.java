package ru.tolstikhin.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private int transaction_id;

    @Column(name = "user_id")
    private int user_id;

    @Column(name = "transaction_date")
    private LocalDateTime transaction_date;

    public Transaction() {}

    public Transaction(int user_id, LocalDateTime transaction_date) {
        this.user_id = user_id;
        this.transaction_date = transaction_date;
    }
}
