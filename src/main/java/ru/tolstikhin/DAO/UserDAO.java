package ru.tolstikhin.DAO;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.tolstikhin.HibernateUtil;
import ru.tolstikhin.entities.Role;
import ru.tolstikhin.entities.User;

public class UserDAO {
    private final int ADMIN_ID = 1;
    private final int PASSENGER_ID = 2;

    private SessionFactory sessionFactory;

    private Session session;

    public UserDAO() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    public void insertData(String login, String password) {
        session = HibernateUtil.openSession();
        Role passenger = new Role();
        passenger.setId(PASSENGER_ID);
        User user = new User(login, password, passenger);
//        String queryInsert = "INSERT INTO users(login, password, role) select login, password, passenger FROM user";
//        Query query = session.createQuery(queryInsert);
//        int result = query.executeUpdate();
        session.persist(user);
        session.getTransaction().commit();
        session.close();
    }

    public boolean selectData(String login, String password) {
        session = HibernateUtil.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> cq = builder.createQuery(User.class);
        Root<User> root = cq.from(User.class);
        cq.select(root).where(builder.and(builder.equal(root.get("login"), login), builder.equal(root.get("password"), password)));
        Query query = session.createQuery(cq);
        return !query.getResultList().isEmpty();
//        Query query = session.createQuery("FROM User users WHERE users.login = :paramLogin AND users.password = :paramPassword");
//        query.setParameter("paramLogin", login);
//        query.setParameter("paramPassword", password);
//        return query.uniqueResult() != null;
    }

    public boolean selectLogin(String login) {
        session = HibernateUtil.openSession();
        Query query = session.createQuery("FROM User users WHERE users.login = :paramLogin");
        query.setParameter("paramLogin", login);
        return query.uniqueResult() != null;
    }

    public String selectLoginName(int id) {
        session = HibernateUtil.openSession();
        Query query = session.createQuery("SELECT login FROM User users WHERE id = :paramId");
        query.setParameter("paramId", id);
        return (String) query.uniqueResult();
    }

    public int selectId(String login, String password) {
        session = HibernateUtil.openSession();
        Query query = session.createQuery("SELECT id FROM User users WHERE login = :paramLogin AND password = :paramPassword");
        query.setParameter("paramLogin", login);
        query.setParameter("paramPassword", password);
        return (int) query.uniqueResult();
    }

    public void updateInputData(String password, String login) {
        session = HibernateUtil.openSession();
        Query query = session.createQuery("UPDATE User SET password = :paramPassword WHERE login = :paramLogin");
        query.setParameter("paramPassword", password);
        query.setParameter("paramLogin", login);
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

//    public void updateUserData(int id, String surname, String name, String patronymic, Date birthday, String passport) {
//        session = HibernateUtil.openSession();
//        Query query = session.createQuery("UPDATE User SET surname = :paramSurname WHERE id = :paramId");
//        query.setParameter("paramSurname", surname);
//        query.setParameter("paramName", name);
//        query.setParameter("paramPatronymic", patronymic);
//        query.setParameter("paramBirthday", birthday);
//        query.setParameter("paramPassport", passport);
//        query.setParameter("paramLogin", id);
//        query.executeUpdate();
//        session.getTransaction().commit();
//        session.close();
//    }
}
