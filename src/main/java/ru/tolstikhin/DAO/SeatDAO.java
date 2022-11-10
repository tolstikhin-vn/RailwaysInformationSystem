package ru.tolstikhin.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.tolstikhin.HibernateUtil;

public class SeatDAO {

    private final SessionFactory sessionFactory;

    private Session session;

    public SeatDAO() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    public Long findFreeSeatsCount(int trainNumber, int wagonType) {
        session = HibernateUtil.openSession();
        Query query = session.createQuery("SELECT COUNT(s.seat_number)" +
                "FROM Seat s\n" +
                "INNER JOIN Wagon w ON w.wagon_number = s.wagon \n" +
                "WHERE s.booked = false AND w.train = :paramTrainNumber AND w.wagon_type = :paramWagonType");
        query.setParameter("paramTrainNumber", trainNumber);
        query.setParameter("paramWagonType", wagonType);
        Long FreeSeatsCount = (Long) query.getSingleResult();
        session.close();
        return FreeSeatsCount;
    }

    public float selectSeatPrice(int trainNumber, int wagonType) {
        session = HibernateUtil.openSession();
        Query query = session.createQuery("SELECT s.seat_price FROM Seat s\n" +
                "INNER JOIN Wagon w ON w.wagon_number = s.wagon \n" +
                "WHERE s.booked = false AND w.train = :paramTrainNumber AND w.wagon_type = :paramWagonType");
        query.setParameter("paramTrainNumber", trainNumber);
        query.setParameter("paramWagonType", wagonType);
        float seatPrice = (float) query.uniqueResult();
        session.close();
        return seatPrice;
    }
}
