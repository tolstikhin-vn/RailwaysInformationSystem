package ru.tolstikhin.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.tolstikhin.DAO.UserDAO;
import ru.tolstikhin.MainApp;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    public static Stage authStage = null;
    public static Stage accountStage = null;

    private static int userId;

    public static int getUserId() {
        return userId;
    }
    @FXML
    private Text arrivalDateText;

    @FXML
    private Text arrivalTimeText;

    @FXML
    private TextField cityFromField;

    @FXML
    private TextField cityToField;

    @FXML
    private DatePicker departureDate;

    @FXML
    private Text departureDateText;

    @FXML
    private Text departureTimeText;

    @FXML
    private ImageView leftArrow;

    @FXML
    private Text logInText;

    @FXML
    private Text numOfPage;

    @FXML
    private Text pageNumber;

    @FXML
    private Text personalAccount;

    @FXML
    private ImageView rightArrow;

    @FXML
    private Text routeNameText;

    @FXML
    private Pane routePanel1;

    @FXML
    private Text stationFromText;

    @FXML
    private Text stationToText;

    @FXML
    private Text trainNameText;

    @FXML
    private Text travelTimeText;


    @FXML
    void showAuthorizationWindow(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/authorization.fxml")));
        Stage authorizationWindow = new Stage();
        try {
            authorizationWindow.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        authorizationWindow.setTitle("Вход");
        authorizationWindow.initModality(Modality.WINDOW_MODAL);
        authorizationWindow.initOwner(MainApp.primaryStage);
        authorizationWindow.show();

        authStage = authorizationWindow;
    }

    @FXML
    void showAccountWindow(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/account.fxml")));
        Stage accountWindow = new Stage();
        try {
            accountWindow.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        accountWindow.setTitle("Профиль пользователя");
        accountWindow.initModality(Modality.WINDOW_MODAL);
        accountWindow.initOwner(MainApp.primaryStage);
        accountWindow.show();

        accountStage = accountWindow;
    }

    /**
     * Сделать прошедшие дни относительно текущего неактивными для выбора
     */
    private void disablePastDates() {
        departureDate.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0 );
            }
        });
    }

    /**
     * Изменить logInText на personalAccount, если пользователя вошел в аккаунт, для возможности перехода в него
     */
    private void setPersonalAccount() {
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setPersonalAccount();
        disablePastDates();
    }
}
