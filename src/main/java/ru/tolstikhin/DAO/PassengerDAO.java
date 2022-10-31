package ru.tolstikhin.DAO;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.selector.spi.StrategyCreator;
import org.hibernate.query.Query;
import ru.tolstikhin.HibernateUtil;
import ru.tolstikhin.controllers.MainController;
import ru.tolstikhin.entities.Passenger;
import ru.tolstikhin.entities.User;

import java.sql.Date;
import java.util.List;

public class PassengerDAO {

    private SessionFactory sessionFactory;

    private Session session;

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    private Passenger passenger;

    public PassengerDAO() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    public void insertData(int id, String name, String surname, String patronymic, Date birthDate, String passport) {
        session = HibernateUtil.openSession();
        passenger = new Passenger(id, name, surname, patronymic, birthDate, passport);
        setPassenger(passenger);
        session.merge(passenger);
        session.getTransaction().commit();
        session.close();
//        CriteriaBuilder cb = session.getCriteriaBuilder();
//        CriteriaUpdate<Passenger> criteriaUpdate = cb.createCriteriaUpdate(Passenger.class);
//        Root<Passenger> root = criteriaUpdate.from(Passenger.class);
//        criteriaUpdate.set("id", id);
////        criteriaUpdate.where(cb.equal(root.get("itemPrice"), oldPrice));
//
//        session.createQuery(criteriaUpdate).executeUpdate();
//
//        session.getTransaction().commit();
//        session.close();
    }

    public Passenger selectData() {
        session = HibernateUtil.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Passenger> cq = builder.createQuery(Passenger.class);
        Root<Passenger> root = cq.from(Passenger.class);
        System.out.println("---------->" + MainController.getUserId());
        cq.select(root).where(builder.equal(root.get("id"), MainController.getUserId()));
        Query query = session.createQuery(cq);
        List<Passenger> passengerList = query.getResultList();
        return passengerList.get(0);
    }
}
