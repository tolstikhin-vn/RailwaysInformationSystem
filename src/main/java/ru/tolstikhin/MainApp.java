package ru.tolstikhin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.Session;

import java.io.IOException;
import java.util.Objects;

public class MainApp extends Application {
    public static Session session = null;
    public static Stage primaryStage;

    @Override
    public void start(Stage newPrimaryStage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("sample.fxml")));

        Scene mainScene = new Scene(root);
        newPrimaryStage.setTitle("РЖД");
        newPrimaryStage.setScene(mainScene);
        newPrimaryStage.show();

        primaryStage = newPrimaryStage;
    }

    @Override
    public void stop() {
    }

//    public static void addUser(String log, String pass) {
//        SessionFactory sessionFactory = SessionFactory.getSessionFactory();
//        Session session = HibernateUtil.getSessionFactory().openSession();
////        session = HibernateUtility.getSessionFactory().getCurrentSession();
//        session.beginTransaction();
//        User user = new User(log, pass, 1);
//        session.save(user);
//        System.out.println(user);
//        session.getTransaction().commit();
//        session.close();
//    }

    public static void main(String[] args) {
        launch(args);

//        essionFactory sessionFactory = new Configuration()
//                .addAnnotatedClass(User.class)
//                .buildSessionFactory();
//         Создание
//        session = sessionFactory.getCurrentSession();
//        session.beginTransaction();
//        User user = new User("log", "pass", 1);
//        session.save(user);
//        System.out.println(user);
//        session.getTransaction().commit();

        // Чтение
//        session = sessionFactory.getCurrentSession();
//        session.beginTransaction();
//        User user = session.get(User.class, 1);
//        System.out.println(user);
//        session.getTransaction().commit();

        // Обновление
//        session = sessionFactory.getCurrentSession();
//        session.beginTransaction();
//        User user = session.get(User.class, 1);
//        user.setLogin("log2");
//        System.out.println(user);
//        session.getTransaction().commit();

        // Удаление
//        session = sessionFactory.getCurrentSession();
//        session.beginTransaction();
//        User user = session.get(User.class, 1);
//        session.remove(user);
//        System.out.println(user);
//        session.getTransaction().commit();

//        sessionFactory.close();
    }

//    public static set
}