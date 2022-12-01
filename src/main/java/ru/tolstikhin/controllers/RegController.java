package ru.tolstikhin.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import ru.tolstikhin.DAO.UserDAO;

import java.net.URL;
import java.util.ResourceBundle;

public class RegController implements Initializable {

    private final String REGEX = "^[a-zA-Z0-9]{8,}$";
    private final String NULL_FX_BORDER_COLOR = "-fx-border-color: null";
    private final String RED_FX_BORDER_COLOR = "-fx-border-color: red";

    @FXML
    private TextField regLoginField;

    @FXML
    private Button regButton;

    @FXML
    private PasswordField regPasswordField;
    @FXML
    void registerUser(MouseEvent event) {
        UserDAO userDAO = new UserDAO();
        if (!userDAO.isLoginExist(regLoginField.getText())) {
            System.out.println("логин существует");
        } else {
            userDAO.insertUserData(regLoginField.getText(), regPasswordField.getText());
            AuthController.regStage.close();
            MainController.authStage.show();
        }
    }

    private void setLogTextFieldListener() {
        UserDAO userDAO = new UserDAO();
        regLoginField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (userDAO.isLoginExist(regLoginField.getText()) && regLoginField.getText().matches(REGEX)) {
                regLoginField.setStyle(NULL_FX_BORDER_COLOR);
                if (regPasswordField.getText().matches(REGEX)) {
                    regButton.setDisable(false);
                }
            } else {
                regLoginField.setStyle(RED_FX_BORDER_COLOR);
                regButton.setDisable(true);
            }
        });
    }

    private void setPassTextFieldListener() {
        UserDAO userDAO = new UserDAO();
        regPasswordField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (regPasswordField.getText().matches(REGEX)) {
                regPasswordField.setStyle(NULL_FX_BORDER_COLOR);
                if (userDAO.isLoginExist(regLoginField.getText()) && regLoginField.getText().matches(REGEX)) {
                    regButton.setDisable(false);
                }
            } else {
                regButton.setDisable(true);
                regPasswordField.setStyle(RED_FX_BORDER_COLOR);
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setLogTextFieldListener();
        setPassTextFieldListener();
    }
}
