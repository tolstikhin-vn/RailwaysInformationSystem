package ru.tolstikhin.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import ru.tolstikhin.DAO.PassengerDAO;
import ru.tolstikhin.DAO.UserDAO;
import ru.tolstikhin.MainApp;
import ru.tolstikhin.entities.Passenger;
import ru.tolstikhin.entities.User;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class AccountController extends MainApp implements Initializable {

    public final int ID_NON_EXISTENT_USER = 0;
    private final String FX_BACKGROUND_COLOR_RED = "-fx-background-color: red";
    private final String FX_BACKGROUND_NO_COLOR = "-fx-background-color: null";
    private final String PASSENGERS_TEXT = "Пассажиры";

    private int userId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @FXML
    private Button addPassenger;

    @FXML
    private Button addPassengerButton;

    @FXML
    private Pane femaleGender;

    @FXML
    private Pane infAboutPassenger;

    @FXML
    private Pane maleGender;

    @FXML
    private TextField myEmailField;

    @FXML
    private TextField myLoginField;

    @FXML
    private TextField myNameField;

    @FXML
    private TextField mySurnameField;

    @FXML
    private Text orders;

    @FXML
    private DatePicker passBirthday;

    @FXML
    private Pane passFemaleGender;

    @FXML
    private Pane passMaleGender;

    @FXML
    private TextField passName;

    @FXML
    private TextField passPassportData;

    @FXML
    private TextField passPatronymic;

    @FXML
    private TextField passSurname;

    @FXML
    private Text passengers;

    @FXML
    private ComboBox<String> passengersList;

    @FXML
    private Pane passengersPane;

    @FXML
    private Text profile;

    @FXML
    private Pane profilePane;

    @FXML
    private Button savePassenger;

    @FXML
    void chooseFemaleGender(MouseEvent event) {
        maleGender.setStyle(FX_BACKGROUND_NO_COLOR);
        femaleGender.setStyle(FX_BACKGROUND_COLOR_RED);

    }

    @FXML
    void chooseMaleGender(MouseEvent event) {
        femaleGender.setStyle(FX_BACKGROUND_NO_COLOR);
        maleGender.setStyle(FX_BACKGROUND_COLOR_RED);
    }

    @FXML
    void choosePassFemaleGender(MouseEvent event) {
        passMaleGender.setStyle(FX_BACKGROUND_NO_COLOR);
        passFemaleGender.setStyle(FX_BACKGROUND_COLOR_RED);
    }

    @FXML
    void choosePassMaleGender(MouseEvent event) {
        passFemaleGender.setStyle(FX_BACKGROUND_NO_COLOR);
        passMaleGender.setStyle(FX_BACKGROUND_COLOR_RED);
    }

    @FXML
    void logOut(MouseEvent event) throws IOException {
        AuthController.setUserId(ID_NON_EXISTENT_USER);
        start(MainApp.primaryStage);
        MainController.accountStage.close();
    }

    @FXML
    void saveData(MouseEvent event) {
        UserDAO userDAO = new UserDAO();

        userDAO.insertData(getUserId(),
                myNameField.getText(),
                mySurnameField.getText(),
                myEmailField.getText(),
                getGender(maleGender, femaleGender));
        showAlertWindow();
    }

    @FXML
    void showOrders(MouseEvent event) {

    }

    @FXML
    void showPassengers(MouseEvent event) {
        profilePane.setVisible(false);
        infAboutPassenger.setVisible(false);
        passengersPane.setVisible(true);
        if (!passengersList.getSelectionModel().isEmpty()) {
            passengersList.getSelectionModel().clearSelection();
            passengersList.setPromptText(PASSENGERS_TEXT);
        }
    }

    @FXML
    void showProfile(MouseEvent event) {
        passengersPane.setVisible(false);
        profilePane.setVisible(true);
        infAboutPassenger.setVisible(false);
    }

    @FXML
    void showPassengersOnClicked(MouseEvent event) {
        showPassengersList(passengersList);
    }

    @FXML
    void showAddingPassenger(MouseEvent event) {
        passengersList.getSelectionModel().clearSelection();
        passengersList.setPromptText(PASSENGERS_TEXT);
        infAboutPassenger.setVisible(true);
        addPassenger.setVisible(true);
        savePassenger.setVisible(false);
    }

    @FXML
    void savePassengerData(MouseEvent event) {
        PassengerDAO passengerDAO = new PassengerDAO();
        List<Passenger> passengerList = passengerDAO.selectPassengers(MainController.getUserId());
        Passenger passenger = passengerList.get(passengersList.getSelectionModel().getSelectedIndex());

        passengerDAO.updateData(passenger.getPassengerId(),
                passenger.getName(),
                passenger.getSurname(),
                passenger.getPatronymic(),
                passenger.getBirthdate(),
                passenger.getPassportData(),
                passenger.getGender());
        showAlertWindow();
    }

    @FXML
    void addNewPassenger() {
        PassengerDAO passengerDAO = new PassengerDAO();
        passengerDAO.insertData(MainController.getUserId(),
                passName.getText(), passSurname.getText(),
                passPatronymic.getText(),
                java.sql.Date.valueOf(passBirthday.getValue()),
                passPassportData.getText(),
                getGender(passMaleGender, passFemaleGender));
    }

    /**
     * Показать информационное окно
     */
    private void showAlertWindow() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Информация");
        alert.setHeaderText(null);
        alert.setContentText("Данные успешно сохранены");

        alert.showAndWait();
    }

    /**
     * Получить ообозначение пола пользователя
     * @param maleGenderPane Pane с мужским полом
     * @param femaleGenderPane Pane с женским полом
     * @return обозначение пола
     */
    private String getGender(Pane maleGenderPane, Pane femaleGenderPane) {
        // Если стилистически выделен maleGenderPane, пол мужжской, иначе, если femaleGenderPane, - женский
        if (Objects.equals(maleGenderPane.getStyle(), FX_BACKGROUND_COLOR_RED)) {
            return "М";
        } else if (Objects.equals(femaleGenderPane.getStyle(), FX_BACKGROUND_COLOR_RED)) {
            return "Ж";
        }
        return null;
    }

    /**
     * Заполнить персональные данные пользователя в профиле
     */
    private void fillOtherData() {
        UserDAO userDAO = new UserDAO();
        User user = userDAO.selectUser();
        mySurnameField.setText(user.getSurname());
        myNameField.setText(user.getName());
        myEmailField.setText(user.getEmail());
        if (Objects.equals(user.getGender(), "М")) {
            maleGender.setStyle(FX_BACKGROUND_COLOR_RED);
        } else if (Objects.equals(user.getGender(), "Ж")) {
            femaleGender.setStyle(FX_BACKGROUND_COLOR_RED);
        }
    }

    /**
     * Заполнить поле логина в профиле пользователя
     */
    private void fillLogin() {
        UserDAO userDAO = new UserDAO();
        setUserId(MainController.getUserId());
        myLoginField.setText(userDAO.selectLoginName(getUserId()));
    }

    /**
     * Заполнить ComboBox пассажиров пассажирами, связанными с авторизированным аккаунтом
     * @param passList ComboBox пассажиров
     */
    public void showPassengersList(ComboBox<String> passList) {
        ObservableList<String> passengerObservList = FXCollections.observableArrayList();
        PassengerDAO passengerDAO = new PassengerDAO();
        List<Passenger> passengerList = passengerDAO.selectPassengers(MainController.getUserId());
        for (Passenger passenger : passengerList) {
            passengerObservList.add(passenger.getSurname() + " " + passenger.getName() + " " + passenger.getPatronymic());
        }
        // Добавляем в ComboBox результирующий список
        passList.setItems(passengerObservList);
    }

    /**
     *
     * @param passengerIndex индекс выбранного пассажира в ComboBox
     */
    private void showInfAboutPassenger(int passengerIndex) {
        // Если пассажир выбран, заполняем поля его данными, иначе -
        if (!passengersList.getSelectionModel().isEmpty()) {
            addPassenger.setVisible(false);
            savePassenger.setVisible(true);
            PassengerDAO passengerDAO = new PassengerDAO();
            List<Passenger> passengerList = passengerDAO.selectPassengers(MainController.getUserId());
            infAboutPassenger.setVisible(true);
            Passenger passenger = passengerList.get(passengerIndex);
            passSurname.setText(passenger.getSurname());
            passName.setText(passenger.getName());
            passPatronymic.setText(passenger.getPatronymic());
            passBirthday.setValue(passenger.getBirthdate().toLocalDate());
            passPassportData.setText(passenger.getPassportData());
            if (Objects.equals(passenger.getGender(), "М")) {
                passMaleGender.setStyle(FX_BACKGROUND_COLOR_RED);
            } else if (Objects.equals(passenger.getGender(), "Ж")) {
                passFemaleGender.setStyle(FX_BACKGROUND_COLOR_RED);
            }
        } else {
            passSurname.setText("");
            passName.setText("");
            passPatronymic.setText("");
            passBirthday.setValue(null);
            passPassportData.setText("");
            passMaleGender.setStyle(FX_BACKGROUND_NO_COLOR);
            passFemaleGender.setStyle(FX_BACKGROUND_NO_COLOR);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Навесить обработчики событий кликов мыши по элементам выпадающего списка пассажиров
        passengersList.setOnAction(event -> showInfAboutPassenger(passengersList.getSelectionModel().getSelectedIndex()));
        fillLogin();
        fillOtherData();
    }
}
