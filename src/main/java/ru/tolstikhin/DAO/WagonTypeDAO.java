package ru.tolstikhin.DAO;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.tolstikhin.HibernateUtil;
import ru.tolstikhin.entities.Wagon;

import java.util.ArrayList;
import java.util.List;

public class WagonTypeDAO {

    private SessionFactory sessionFactory;

    private Session session;

    public WagonTypeDAO() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    public List<String> selectWagonsTypesNames(int trainNumber) {
        session = HibernateUtil.openSession();
        Query query = session.createQuery("SELECT wt.type_name FROM WagonType wt INNER JOIN Wagon w ON w.wagon_type = wt.type_id WHERE w.train = :paramTrainNumber");
        query.setParameter("paramTrainNumber", trainNumber);
        List<String> wagonsTypesNamesList = (ArrayList<String>) query.getResultList();
        session.close();
        return wagonsTypesNamesList;
    }

//    public Long selectFreeSeatsCount() {
//        session = HibernateUtil.openSession();
//        Query query = session.createQuery("SELECT wt.type_name FROM WagonType wt INNER JOIN Wagon w ON w.wagon_type = wt.type_id WHERE w.train = :paramTrainNumber");
//        query.setParameter("paramTrainNumber", trainNumber);
//        List<String> wagonsTypesNamesList = (ArrayList<String>) query.getResultList();
//        session.close();
//        return wagonsTypesNamesList;
//    }

    public int selectWagonTypeId(String wagonTypeName) {
        session = HibernateUtil.openSession();
        Query query = session.createQuery("SELECT wt.type_id FROM WagonType wt WHERE wt.type_name = :paramWagonTypeName");
        query.setParameter("paramWagonTypeName", wagonTypeName);
        int wagonTypeId = (int) query.uniqueResult();
        session.close();
        return wagonTypeId;
    }
}
