package ru.tolstikhin.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import ru.tolstikhin.DAO.PassengerDAO;
import ru.tolstikhin.DAO.UserDAO;
import ru.tolstikhin.MainApp;
import ru.tolstikhin.entities.Passenger;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class AccountController extends MainApp implements Initializable {

    PassengerDAO passengerDAO;
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    private int userId;

    @FXML
    private DatePicker myBirthdayField;

    @FXML
    private TextField myLoginField;

    @FXML
    private TextField myNameField;

    @FXML
    private TextField myPassportField;

    @FXML
    private TextField myPatronymicField;

    @FXML
    private TextField mySurnameField;
    @FXML
    void logOut(MouseEvent event) throws IOException {
        AuthController.setUserId(0);
        start(MainApp.primaryStage);
        MainController.accountStage.close();
    }

    @FXML
    void saveData(MouseEvent event) {
        PassengerDAO passengerDAO = new PassengerDAO();
        passengerDAO.insertData(getUserId(),
                myNameField.getText(),
                mySurnameField.getText(),
                myPatronymicField.getText(),
                Date.valueOf(myBirthdayField.getValue()),
                myPassportField.getText());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Информация");
        alert.setHeaderText(null);
        alert.setContentText("Данные успешно сохранены");

        alert.showAndWait();
    }
    private void fillOtherData() {
        Passenger passenger = passengerDAO.selectData();
        myNameField.setText(passenger.getName());
        mySurnameField.setText(passenger.getSurname());
        myPatronymicField.setText(passenger.getPatronymic());
        myBirthdayField.setValue(passenger.getBirthdate().toLocalDate());
        myPassportField.setText(passenger.getPassport_data());
    }

    private void fillLogin() {
        UserDAO userDAO = new UserDAO();
        setUserId(MainController.getUserId());
        myLoginField.setText(userDAO.selectLoginName(getUserId()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillLogin();

        passengerDAO = new PassengerDAO();
        if (passengerDAO.passengerExists()) {
            fillOtherData();
        }
    }
}
