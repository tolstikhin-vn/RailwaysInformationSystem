package ru.tolstikhin.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.tolstikhin.HibernateUtil;
import ru.tolstikhin.entities.Ticket;

import java.sql.Time;
import java.util.Date;

public class TicketDAO {

    private SessionFactory sessionFactory;

    private Session session;

    public TicketDAO() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    // Вставить в таблицу билетов новый купленный билет
    public void insertTicket(int userId, String trainName, int wagonNumber, String wagonType, int seatNumber,
                             String name, String surname, String patronymic, String passportData, Date birthDate,
                             Date departureDate, Time departureTime, Date arrivalDate, Time arrivalTime,
                             String cityFrom, String cityTo, double price) {
        session = HibernateUtil.openSession();
        Ticket ticket = new Ticket(userId, trainName, wagonNumber, wagonType, seatNumber, name, surname, patronymic, passportData, (java.sql.Date) birthDate, (java.sql.Date) departureDate, departureTime, (java.sql.Date) arrivalDate, arrivalTime, cityFrom, cityTo, price);
        session.persist(ticket);
        session.getTransaction().commit();
        session.close();
    }
}
