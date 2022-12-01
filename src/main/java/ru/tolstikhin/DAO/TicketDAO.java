package ru.tolstikhin.DAO;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import ru.tolstikhin.HibernateUtil;
import ru.tolstikhin.entities.Ticket;
import ru.tolstikhin.entities.User;
import ru.tolstikhin.entities.Wagon;

import java.sql.Time;
import java.util.Date;
import java.util.List;

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
                             String cityFrom, String stationFrom, String cityTo, String stationTo, double price) {
        session = HibernateUtil.openSession();
        Ticket ticket = new Ticket(userId, trainName, wagonNumber, wagonType, seatNumber, name, surname, patronymic,
                passportData, (java.sql.Date) birthDate, (java.sql.Date) departureDate, departureTime,
                (java.sql.Date) arrivalDate, arrivalTime, cityFrom, stationFrom,  cityTo, stationTo, price);
        session.persist(ticket);
        session.getTransaction().commit();
        session.close();
    }

    public List<Ticket> selectListOfTickets(int userId) {
        session = HibernateUtil.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Ticket> cq = builder.createQuery(Ticket.class);
        Root<Ticket> root = cq.from(Ticket.class);
        cq.select(root).where(builder.equal(root.get("user_id"), userId));
        Query query = session.createQuery(cq);
        List<Ticket> listOfTickets = query.getResultList();
        session.close();
        return listOfTickets;
    }

    public void deleteTicket(int ticketNumber) {
        session = HibernateUtil.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaDelete<Ticket> criteriaDelete = builder.createCriteriaDelete(Ticket.class);
        Root<Ticket> root = criteriaDelete.from(Ticket.class);
        criteriaDelete.where(builder.equal(root.get("ticket_number"), ticketNumber));
        session.createQuery(criteriaDelete).executeUpdate();
        session.close();
    }

    public boolean isTicketExist(int ticketNumber) {
        session = HibernateUtil.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Ticket> cq = builder.createQuery(Ticket.class);
        Root<Ticket> root = cq.from(Ticket.class);
        cq.select(root).where(builder.equal(root.get("ticket_number"), ticketNumber));
        Query<Ticket> query = session.createQuery(cq);
        boolean result = !query.getResultList().isEmpty();
        session.close();
        return result;
    }

    public Ticket selectTicket(int ticketNumber) {
        session = HibernateUtil.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Ticket> cq = builder.createQuery(Ticket.class);
        Root<Ticket> root = cq.from(Ticket.class);
        cq.select(root).where(builder.equal(root.get("ticket_number"), ticketNumber));
        Query query = session.createQuery(cq);
        Ticket ticket = (Ticket) query.getSingleResult();
        session.close();
        return ticket;
    }
}
