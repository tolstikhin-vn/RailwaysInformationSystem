package ru.tolstikhin.DAO;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.tolstikhin.HibernateUtil;
import ru.tolstikhin.entities.Passenger;
import java.sql.Date;
import java.util.List;

public class PassengerDAO {

    private SessionFactory sessionFactory;

    private Session session;

    public PassengerDAO() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    /**
     * Выбрать список пассажиров
     * @param userId id пользователя
     * @return список пассажиров, относящихся к определенному пользователю
     */
    public List<Passenger> selectPassengers(int userId) {
        session = HibernateUtil.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Passenger> cq = builder.createQuery(Passenger.class);
        Root<Passenger> root = cq.from(Passenger.class);
        cq.select(root).where(builder.equal(root.get("id"), userId));
        Query query = session.createQuery(cq);
        List<Passenger> resultList = query.getResultList();
        session.close();
        return resultList;
    }

    /**
     * Обновить данные о пассажире
     * @param passId id пассажира
     * @param name имя пассажира
     * @param surname фамилия пассажира
     * @param patronymic отчество пассажира
     * @param birthdate дата рождения пассажира
     * @param passportData паспортные данные пассажира
     * @param gender пол пассажира
     */
    public void updateData(int passId, String name, String surname, String patronymic, Date birthdate, String passportData, String gender) {
        session = HibernateUtil.openSession();
        Passenger passenger = session.get(Passenger.class, passId);
        passenger.setName(name);
        passenger.setSurname(surname);
        passenger.setPatronymic(patronymic);
        passenger.setBirthdate(birthdate);
        passenger.setPassportData(passportData);
        passenger.setGender(gender);
        session.merge(passenger);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * Добавить пассажира
     * @param userId id пользователя
     * @param name имя пассажира
     * @param surname фамилия пассажира
     * @param patronymic отчество пассажира
     * @param birthdate дата рождения пассажира
     * @param passportData паспортные данные пассажира
     * @param gender пол пассажира
     */
    public void insertData(int userId,  String name, String surname, String patronymic, Date birthdate, String passportData, String gender) {
        session = HibernateUtil.openSession();
        Passenger passenger = new Passenger(userId, name, surname, patronymic, birthdate, passportData, gender);
        session.persist(passenger);
        session.getTransaction().commit();
        session.close();
    }

}
