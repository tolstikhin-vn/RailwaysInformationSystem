package ru.tolstikhin.DAO;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.tolstikhin.HibernateUtil;
import ru.tolstikhin.controllers.MainController;
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

    /**
     * Вставить в таблицу пользователей нового пользователя
     * @param login логин
     * @param password пароль
     */
    public void insertUserData(String login, String password) {
        session = HibernateUtil.openSession();
        Role passenger = new Role();
        passenger.setId(PASSENGER_ID);
        User user = new User(login, password, passenger);
        session.persist(user);
        session.getTransaction().commit();
        session.close();
    }

    public boolean isLoginExist(String login) {
        session = HibernateUtil.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> cq = builder.createQuery(User.class);
        Root<User> root = cq.from(User.class);
        cq.select(root).where(builder.equal(root.get("login"), login));
        Query<User> query = session.createQuery(cq);
        boolean result = query.getResultList().isEmpty();
        session.close();
        return result;
    }

    /**
     * Проверить существование пользователя с такими данными
     * @param login логин
     * @param password пароль
     * @return true - пользователь существует, иначе - false
     */
    public boolean isSelectData(String login, String password) {
        session = HibernateUtil.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> cq = builder.createQuery(User.class);
        Root<User> root = cq.from(User.class);
        cq.select(root).where(builder.and(builder.equal(root.get("login"), login), builder.equal(root.get("password"), password)));
        Query<User> query = session.createQuery(cq);
        boolean result = !query.getResultList().isEmpty();
        session.close();
        return result;
    }

    /**
     * Получить пользователя
     * @return пользователь
     */
    public User selectUser() {
        session = HibernateUtil.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> cq = builder.createQuery(User.class);
        Root<User> root = cq.from(User.class);
        cq.select(root).where(builder.equal(root.get("id"), MainController.getUserId()));
        Query<User> query = session.createQuery(cq);
        User user = query.getSingleResult();
        session.close();
        return user;
    }

    /**
     * Проверить, существует ли пользователь с таким логином
     * @param login логин
     * @return true - существует, иначе - false
     */
    public boolean userExists(String login) {
        session = HibernateUtil.openSession();
        Query query = session.createQuery("FROM User users WHERE users.login = :paramLogin");
        query.setParameter("paramLogin", login);
        boolean userExists = query.uniqueResult() != null;
        session.close();
        return userExists;
    }

    /**
     * Получить логин пользователя по его id
     * @param id id пользователя
     * @return логин
     */
    public String selectLoginName(int id) {
        session = HibernateUtil.openSession();
        Query query = session.createQuery("SELECT login FROM User users WHERE id = :paramId");
        query.setParameter("paramId", id);
        String login = (String) query.uniqueResult();
        session.close();
        return login;
    }

    /**
     * Получить id пользователя по его логину и паролю
     * @param login логин
     * @param password пароль
     * @return id пользователя
     */
    public int selectId(String login, String password) {
        session = HibernateUtil.openSession();
        Query query = session.createQuery("SELECT id FROM User users WHERE login = :paramLogin AND password = :paramPassword");
        query.setParameter("paramLogin", login);
        query.setParameter("paramPassword", password);
        int id = (int) query.uniqueResult();
        session.close();
        return id;
    }

    /**
     * Обновить пароль для входа в аккаунт
     * @param password новый пароль
     * @param login логин
     */
    public void updateInputData(String password, String login) {
        session = HibernateUtil.openSession();
        Query query = session.createQuery("UPDATE User SET password = :paramPassword WHERE login = :paramLogin");
        query.setParameter("paramPassword", password);
        query.setParameter("paramLogin", login);
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    /**
     * Добавить в таблицу пользователей персональные данные
     * @param id id пользователя
     * @param name имя пользователя
     * @param surname фамилия пользователя
     * @param email электронная почта пользователя
     * @param gender пол пользователя
     */
    public void insertData(int id, String name, String surname, String email, String gender) {
        session = HibernateUtil.openSession();
        User user = session.get(User.class, id);
        System.out.println(user);
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setGender(gender);
        session.merge(user);
        session.getTransaction().commit();
        session.close();
    }
}
