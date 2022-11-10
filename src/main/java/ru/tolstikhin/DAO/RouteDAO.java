package ru.tolstikhin.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.tolstikhin.HibernateUtil;
import ru.tolstikhin.entities.Route;
import ru.tolstikhin.entities.Schedule;

import java.time.LocalDate;
import java.util.List;

public class RouteDAO {

    private final SessionFactory sessionFactory;

    private Session session;

    public RouteDAO() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    public Route selectRoute(int scheduleId) {
        session = HibernateUtil.openSession();
        Query query = session.createQuery("FROM Route r\n" +
                "INNER JOIN Schedule sc ON sc.route_id = r.route_id\n" +
                "WHERE sc.schedule_id = :paramScheduleId");
        query.setParameter("paramScheduleId", scheduleId);
        Route route = (Route) query.uniqueResult();
        session.close();
        return route;
    }
}
