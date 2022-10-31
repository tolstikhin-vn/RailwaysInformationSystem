package ru.tolstikhin.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import ru.tolstikhin.DAO.PassengerDAO;
import ru.tolstikhin.DAO.UserDAO;

import java.net.URL;
import java.util.ResourceBundle;

public class RegController {

    @FXML
    private TextField regLoginField;

    @FXML
    private TextField regPasswordField;
    @FXML
    void registerUser(MouseEvent event) {
        UserDAO userDAO = new UserDAO();
        userDAO.insertData(regLoginField.getText(), regPasswordField.getText());
//        PassengerDAO passengerDAO = new PassengerDAO();
//        passengerDAO.insertData(userDAO.selectId(regLoginField.getText(), regPasswordField.getText()));
        AuthController.regStage.close();
        MainController.authStage.show();
    }
}
