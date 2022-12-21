package ru.tolstikhin.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.tolstikhin.HibernateUtil;
import ru.tolstikhin.entities.Role;
import ru.tolstikhin.entities.Transaction;
import ru.tolstikhin.entities.User;

import java.time.LocalDateTime;

public class TransactionDAO {

    private SessionFactory sessionFactory;

    private Session session;

    public TransactionDAO() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    public void insertTransaction(int userId, LocalDateTime localDateTime) {
        session = HibernateUtil.openSession();
        Transaction transaction = new Transaction(userId, localDateTime);
        session.persist(transaction);
        session.getTransaction().commit();
        session.close();
    }
}
