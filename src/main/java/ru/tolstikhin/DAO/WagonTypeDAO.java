package ru.tolstikhin.DAO;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.tolstikhin.HibernateUtil;
import ru.tolstikhin.entities.WagonType;

import java.util.List;

public class WagonTypeDAO {

    private SessionFactory sessionFactory;

    private Session session;

    public WagonTypeDAO() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    /**
     * Выбрать список названий типов вагонов
     * @param trainNumber номер вагона
     * @return список названий типов вагонов
     */
    public List<String> selectWagonsTypesNames(int trainNumber) {
        session = HibernateUtil.openSession();
        Query query = session.createQuery("SELECT wt.type_name FROM WagonType wt INNER JOIN Wagon w ON w.wagon_type = wt.type_id WHERE w.train = :paramTrainNumber");
        query.setParameter("paramTrainNumber", trainNumber);
        List<String> wagonsTypesNamesList = query.getResultList();
        session.close();
        return wagonsTypesNamesList;
    }

    /**
     * Выбрать тип вагона по его id типа
     * @param typeId id Типа
     * @return тип вагона
     */
    public WagonType selectWagonType(int typeId) {
        session = HibernateUtil.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<WagonType> cq = builder.createQuery(WagonType.class);
        Root<WagonType> root = cq.from(WagonType.class);
        cq.select(root).where(builder.equal(root.get("type_id"), typeId));
        Query query = session.createQuery(cq);
        return (WagonType) query.getSingleResult();
    }

    /**
     * Выбрать id типа вагона
     * @param wagonTypeName название типа вагона
     * @return id типа
     */
    public int selectWagonTypeId(String wagonTypeName) {
        session = HibernateUtil.openSession();
        Query query = session.createQuery("SELECT wt.type_id FROM WagonType wt WHERE wt.type_name = :paramWagonTypeName");
        query.setParameter("paramWagonTypeName", wagonTypeName);
        int wagonTypeId = (int) query.uniqueResult();
        session.close();
        return wagonTypeId;
    }
}
