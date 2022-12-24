package ru.tolstikhin.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.tolstikhin.HibernateUtil;

public class StationDAO {

    private SessionFactory sessionFactory;

    private Session session;

    public StationDAO() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    /**
     * Выбрать название станции отправления
     * @param stationId id станции
     * @return название станции отправления
     */
    public String selectStationFrom(int stationId) {
        session = HibernateUtil.openSession();
        Query query = session.createQuery("SELECT DISTINCT ts.station_name FROM TrainStation ts\n" +
                "INNER JOIN Route r\n" +
                "ON r.station_from = ts.station_id\n" +
                "WHERE ts.station_id = :paramStationId");
        query.setParameter("paramStationId", stationId);
        String stationFrom = (String) query.uniqueResult();
        session.close();
        return stationFrom;
    }

    /**
     * Выбрать название станции назначения
     * @param stationId id станции
     * @return название станции назначения
     */
    public String selectStationTo(int stationId) {
        session = HibernateUtil.openSession();
        Query query = session.createQuery("SELECT DISTINCT ts.station_name FROM TrainStation ts\n" +
                "INNER JOIN Route r\n" +
                "ON r.station_to = ts.station_id\n" +
                "WHERE ts.station_id = :paramStationId");
        query.setParameter("paramStationId", stationId);
        String stationTo = (String) query.uniqueResult();
        session.close();
        return stationTo;
    }
}
