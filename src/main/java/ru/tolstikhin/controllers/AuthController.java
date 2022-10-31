package ru.tolstikhin.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.tolstikhin.DAO.UserDAO;
import ru.tolstikhin.MainApp;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class AuthController extends MainApp implements Initializable {

    public static Stage regStage = null;
    public static Stage recoverStage = null;
//    public Text personalAccount;
    @FXML
    public TextField authLoginField;

    @FXML
    public TextField authPasswordField;
    @FXML
    public Text logEnErrorMessage;

    public static int getUserId() {
        return userId;
    }

    public static void setUserId(int userId) {
        AuthController.userId = userId;
    }

    public static int userId;

    @FXML
    private void showRegWindow(MouseEvent event) {
        Parent registration = null;
        try {
            registration = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("registration.fxml")));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        Scene registrationScene = new Scene(registration);
        Stage registrationWindow = new Stage();
        registrationWindow.setTitle("Регистрация");
        registrationWindow.setScene(registrationScene);

        registrationWindow.initModality(Modality.WINDOW_MODAL);
        registrationWindow.initOwner(MainApp.primaryStage);
//        registrationWindow.initOwner(MainController.authStage);
        registrationWindow.show();
        MainController.authStage.close();

        regStage = registrationWindow;
    }

    @FXML
    private void logInToAccount() throws IOException {
        UserDAO userDAO = new UserDAO();
        if (userDAO.selectData(authLoginField.getText(), authPasswordField.getText())) {
            setUserId(userDAO.selectId(authLoginField.getText(), authPasswordField.getText()));

            start(MainApp.primaryStage);
            MainController.authStage.close();
        } else {
            logEnErrorMessage.setVisible(true);
        }
    }

    @FXML
    private void recoverPassword() {
        Parent recovering = null;
        try {
            recovering = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("recovery.fxml")));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        Scene recoveringScene = new Scene(recovering);
        Stage recoveringWindow = new Stage();
        recoveringWindow.setTitle("Восстановление");
        recoveringWindow.setScene(recoveringScene);

        recoveringWindow.initModality(Modality.WINDOW_MODAL);
        recoveringWindow.initOwner(MainApp.primaryStage);
        MainController.authStage.close();
        recoveringWindow.show();
        recoverStage = recoveringWindow;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        MainController mainController = new MainController();
//        personalAccount = mainController.getPersonalAccount();
    }
}
