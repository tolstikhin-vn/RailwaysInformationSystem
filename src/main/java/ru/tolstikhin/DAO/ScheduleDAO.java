package ru.tolstikhin.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.tolstikhin.HibernateUtil;
import ru.tolstikhin.entities.Route;
import ru.tolstikhin.entities.Schedule;

import java.time.LocalDate;
import java.util.List;

public class ScheduleDAO {

    private final SessionFactory sessionFactory;

    private Session session;

    public ScheduleDAO() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    public List<Schedule> selectSchedules(String cityFrom, String cityTo, LocalDate departureDate) {
        session = HibernateUtil.openSession();
        Query query = session.createQuery("FROM Schedule sc\n" +
                "INNER JOIN Route r ON sc.route_id = r.route_id\n" +
                "INNER JOIN TrainStation ts1 ON ts1.station_id = r.station_from\n" +
                "INNER JOIN City c1 ON c1.city_id = ts1.city\n" +
                "INNER JOIN TrainStation ts2 ON ts2.station_id = r.station_to\n" +
                "INNER JOIN City c2 ON c2.city_id = ts2.city\n" +
                "WHERE c1.city_name = :paramCityFrom AND c2.city_name = :paramCityTo AND sc.departure_date = :paramDepartureDate");
        query.setParameter("paramCityFrom", cityFrom);
        query.setParameter("paramCityTo", cityTo);
        query.setParameter("paramDepartureDate", departureDate);
        List<Schedule> scheduleList = query.getResultList();
        session.close();
        return scheduleList;
    }
}