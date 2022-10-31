package ru.tolstikhin.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
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

public class MainController implements Initializable {

    public static Stage authStage = null;
    public static Stage accountStage = null;

    private static int userId;
//
//    public void setUserId(int userId) {
//        this.userId = userId;
//    }

    public static int getUserId() {
        return userId;
    }
    @FXML
    public Text logInText;

    @FXML
    private Text personalAccount;

    @FXML
    private TextField cityFromField;

    @FXML
    private TextField cityToField;

    @FXML
    private DatePicker departureDate;

    @FXML
    void showAuthorizationWindow(MouseEvent event) {
        Parent authorization = null;
        FXMLLoader loader = new FXMLLoader();
        try {
            authorization = loader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("authorization.fxml")));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        Scene authorizationScene = new Scene(authorization);
        Stage authorizationWindow = new Stage();
        authorizationWindow.setTitle("Вход");
        authorizationWindow.setScene(authorizationScene);

        authorizationWindow.initModality(Modality.WINDOW_MODAL);
        authorizationWindow.initOwner(MainApp.primaryStage);
        authorizationWindow.show();

        authStage = authorizationWindow;
    }


    @FXML
    void showAccountWindow(MouseEvent event) {
        Parent account = null;
        FXMLLoader loader = new FXMLLoader();
        try {
            account = loader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("account.fxml")));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        Scene accountScene = new Scene(account);
        Stage accountWindow = new Stage();
        accountWindow.setTitle("Профиль пользователя");
        accountWindow.setScene(accountScene);

        accountWindow.initModality(Modality.WINDOW_MODAL);
        accountWindow.initOwner(MainApp.primaryStage);
        accountWindow.show();

        accountStage = accountWindow;
    }

    @FXML
    public void findRout(MouseEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userId = AuthController.getUserId();
        if (userId != 0) {
            UserDAO userDAO = new UserDAO();

            logInText.setVisible(false);
            logInText.setDisable(true);

            personalAccount.setVisible(true);
            personalAccount.setDisable(false);

            personalAccount.setText(userDAO.selectLoginName(userId));
        } else {
            logInText.setVisible(true);
            logInText.setDisable(false);

            personalAccount.setVisible(false);
            personalAccount.setDisable(true);
        }
    }
}
