package ru.tolstikhin.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import ru.tolstikhin.DAO.UserDAO;

public class RegController {

    @FXML
    private TextField regLoginField;

    @FXML
    private PasswordField regPasswordField;
    @FXML
    void registerUser(MouseEvent event) {
        UserDAO userDAO = new UserDAO();
        userDAO.insertUserData(regLoginField.getText(), regPasswordField.getText());
        AuthController.regStage.close();
        MainController.authStage.show();
    }
}
