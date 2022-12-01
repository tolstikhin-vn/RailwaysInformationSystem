package ru.tolstikhin.DAO;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.tolstikhin.HibernateUtil;
import ru.tolstikhin.entities.Passenger;
import ru.tolstikhin.entities.Seat;
import ru.tolstikhin.entities.User;
import ru.tolstikhin.entities.Wagon;

import java.sql.Date;
import java.util.List;

public class SeatDAO {

    private final SessionFactory sessionFactory;

    private Session session;

    public SeatDAO() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    /**
     * Получить количество доступных к покупке мест
     * @param trainNumber номер поезда
     * @param wagonType тип поезда
     * @return количество доступных мест
     */
    public Long findFreeSeatsCount(int trainNumber, int wagonType) {
        session = HibernateUtil.openSession();
        Query query = session.createQuery("SELECT COUNT(s.seat_number)" +
                "FROM Seat s\n" +
                "INNER JOIN Wagon w ON w.wagon_number = s.wagon \n" +
                "WHERE w.train = :paramTrainNumber AND w.wagon_type = :paramWagonType AND s.booked = false");
        query.setParameter("paramTrainNumber", trainNumber);
        query.setParameter("paramWagonType", wagonType);
        Long FreeSeatsCount = (Long) query.getSingleResult();
        session.close();
        return FreeSeatsCount;
    }

    /**
     * Выбрать стоимость места
     * @param trainNumber номер поезда
     * @param wagonType тип вагона
     * @return стоимость места
     */
    public float selectSeatPrice(int trainNumber, int wagonType) {
        session = HibernateUtil.openSession();
        Query query = session.createQuery("SELECT s.seat_price FROM Seat s\n" +
                "INNER JOIN Wagon w ON w.wagon_number = s.wagon \n" +
                "WHERE s.booked = false AND w.train = :paramTrainNumber AND w.wagon_type = :paramWagonType");
        query.setParameter("paramTrainNumber", trainNumber);
        query.setParameter("paramWagonType", wagonType);
        float seatPrice = (float) query.getResultList().get(0);
        session.close();
        return seatPrice;
    }

    /**
     * Выбрать список доступных к покупке мест
     * @param wagonNumber номера вагона
     * @return список доступных мест
     */
    public List<Seat> selectFreeSeats(int wagonNumber) {
        session = HibernateUtil.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Seat> cq = builder.createQuery(Seat.class);
        Root<Seat> root = cq.from(Seat.class);
        cq.select(root).where(builder.and(builder.equal(root.get("wagon"), wagonNumber), builder.equal(root.get("booked"), false)));
        Query query = session.createQuery(cq);
        List<Seat> resultList = query.getResultList();
        session.close();
        return resultList;
    }

    /**
     * Выбрать место в вагоне
     * @param seatNumberInWagon номер места в вагоне
     * @param wagonNumber номер вагона
     * @return место
     */
    public Seat selectSeat(int seatNumberInWagon, int wagonNumber) {
        System.out.println(wagonNumber);
        session = HibernateUtil.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Seat> cq = builder.createQuery(Seat.class);
        Root<Seat> root = cq.from(Seat.class);
        cq.select(root).where(builder.and(builder.equal(root.get("wagon"), wagonNumber), builder.equal(root.get("seat_number_in_wagon"), seatNumberInWagon)));
        Query query = session.createQuery(cq);
        Seat resultSeat = (Seat) query.getSingleResult();
        session.close();
        return resultSeat;
    }

    /**
     * Обновить значение занятости места
     * @param seatNumb номер места
     */
    public void updateSeatBooked(int seatNumb, boolean isBooked) {
        session = HibernateUtil.openSession();
        Seat seat = session.get(Seat.class, seatNumb);
        seat.setBooked(isBooked);
        session.merge(seat);
        session.getTransaction().commit();
        session.close();
    }
}
