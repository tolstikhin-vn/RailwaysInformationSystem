package ru.tolstikhin.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.tolstikhin.HibernateUtil;

public class TrainDAO {

    private SessionFactory sessionFactory;

    private Session session;

    public TrainDAO() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    /**
     * Выбрать название поезда
     * @param scheduleId id расписания
     * @return название поезда
     */
    public String selectTrainName(int scheduleId) {
        session = HibernateUtil.openSession();
        Query query = session.createQuery("SELECT trn.train_name FROM TrainName trn \n" +
                "INNER JOIN Train tr ON tr.train_name_id = trn.train_name_id \n" +
                "INNER JOIN Schedule sc ON sc.train_number = tr.train_number\n" +
                "WHERE sc.schedule_id = :paramScheduleId");
        query.setParameter("paramScheduleId", scheduleId);
        String trainName = (String) query.uniqueResult();
        session.close();
        return trainName;
    }
}
