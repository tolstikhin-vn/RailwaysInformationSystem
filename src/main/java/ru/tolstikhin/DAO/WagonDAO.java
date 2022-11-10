package ru.tolstikhin.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.tolstikhin.HibernateUtil;
public class WagonDAO {

    private SessionFactory sessionFactory;

    private Session session;

    public WagonDAO() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    public Long findWagonTypesCount(int trainNumber) {
        session = HibernateUtil.openSession();
        Query query = session.createQuery("SELECT COUNT(wagon_type) FROM Wagon w WHERE w.train = :paramTrainNumber");
        query.setParameter("paramTrainNumber", trainNumber);
        Long wagonTypesCount = (Long) query.getSingleResult();
        session.close();
        return wagonTypesCount;
    }
}
