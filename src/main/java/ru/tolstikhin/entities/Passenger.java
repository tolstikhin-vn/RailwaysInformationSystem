package ru.tolstikhin.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.sql.Date;

@Entity
@Table(name = "passengers")
public class Passenger {

    @Id
    @Column(name = "user_id")
    private int id;

    @OneToOne(mappedBy = "passenger")
    private User user;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "birthdate")
    private Date birthdate;

    @Column(name = "passport_data")
    private String passport_data;

    public Passenger(int id, String name, String surname, String patronymic, Date birthdate, String passport_data) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.birthdate = birthdate;
        this.passport_data = passport_data;
    }

    public Passenger() {}

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public String getPassport_data() {
        return passport_data;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
