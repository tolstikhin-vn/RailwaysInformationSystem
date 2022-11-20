package ru.tolstikhin.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.tolstikhin.HibernateUtil;

public class CityDAO {

    private SessionFactory sessionFactory;

    private Session session;

    public CityDAO() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    /**
     * Выбрать город отправления по id станции
     * @param stationId id станции
     * @return город отправления
     */
    public String selectCityFrom(int stationId) {
        session = HibernateUtil.openSession();
        Query query = session.createQuery("SELECT c.city_name FROM City c\n" +
                "INNER JOIN TrainStation ts ON ts.city = c.city_id\n" +
                "INNER JOIN Route r ON r.station_from = ts.station_id\n" +
                "INNER JOIN Schedule sc ON sc.route_id = r.route_id\n" +
                "WHERE sc.route_id = :paramStationId");
        query.setParameter("paramStationId", stationId);
        String cityFrom = (String) query.getResultList().get(0);
        session.close();
        return cityFrom;
    }

    /**
     * Получаем название города назначения по id станции
     * @param stationId id станции
     * @return город отправления
     */
    public String selectCityTo(int stationId) {
        session = HibernateUtil.openSession();
        Query query = session.createQuery("SELECT c.city_name FROM City c\n" +
                "INNER JOIN TrainStation ts\n" +
                "ON ts.city = c.city_id\n" +
                "INNER JOIN Route r\n" +
                "ON r.station_to = ts.station_id\n" +
                "WHERE r.station_to = :paramStationId");
        query.setParameter("paramStationId", stationId);
        String cityTo = (String) query.getResultList().get(0);
        session.close();
        return cityTo;
    }
}
