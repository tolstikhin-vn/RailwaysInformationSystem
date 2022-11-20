package ru.tolstikhin.DAO;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.tolstikhin.HibernateUtil;
import ru.tolstikhin.entities.Wagon;

import java.util.List;

public class WagonDAO {

    private SessionFactory sessionFactory;

    private Session session;

    public WagonDAO() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    /**
     * Получить количество типов вагонов
     * @param trainNumber номер поезда
     * @return количество типов вагонов
     */
    public Long findWagonTypesCount(int trainNumber) {
        session = HibernateUtil.openSession();
        Query query = session.createQuery("SELECT COUNT(wagon_type) FROM Wagon w WHERE w.train = :paramTrainNumber");
        query.setParameter("paramTrainNumber", trainNumber);
        Long wagonTypesCount = (Long) query.getSingleResult();
        session.close();
        return wagonTypesCount;
    }

    /**
     * Получить список вагонов
     * @param scheduleId id расписания
     * @return список вагонов
     */
    public List<Wagon> getWagons(int scheduleId) {
        session = HibernateUtil.openSession();
        Query query = session.createQuery("FROM Wagon AS w\n" +
                "INNER JOIN Train AS tr ON w.train = tr.train_number\n" +
                "INNER JOIN Schedule AS sc ON sc.train_number = tr.train_number\n" +
                "WHERE sc.schedule_id = :paramId\n" +
                "ORDER BY w.wagon_number_on_train");
        query.setParameter("paramId", scheduleId);
        List<Wagon> wagonList = query.getResultList();
        session.close();
        return wagonList;
    }

    /**
     * Выбрать вагон по его уникальному номеру и номеру вагона в поезде
     * @param wagonNumberOnTrain  номер вагона в поезде
     * @param trainNumber номер поезда
     * @return вагон
     */
    public Wagon selectWagon(int wagonNumberOnTrain, int trainNumber) {
        session = HibernateUtil.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Wagon> cq = builder.createQuery(Wagon.class);
        Root<Wagon> root = cq.from(Wagon.class);
        cq.select(root).where(builder.and(builder.equal(root.get("wagon_number_on_train"), wagonNumberOnTrain),
                builder.equal(root.get("train"), trainNumber)));
        Query query = session.createQuery(cq);
        Wagon wagon = (Wagon) query.getSingleResult();
        session.close();
        return wagon;
    }
}
