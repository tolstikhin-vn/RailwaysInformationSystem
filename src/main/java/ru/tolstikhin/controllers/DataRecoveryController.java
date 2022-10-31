package ru.tolstikhin.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import ru.tolstikhin.DAO.UserDAO;

import java.net.URL;
import java.util.ResourceBundle;

public class DataRecoveryController implements Initializable {

    @FXML
    public TextField newPassField;

    @FXML
    public TextField recoveryLoginField;

    @FXML
    public TextField repeatNewPassField;

    @FXML
    public Button updatePassButton;
    @FXML
    public void checkLogin() {
        UserDAO userDAO = new UserDAO();
        if (userDAO.selectLogin(recoveryLoginField.getText())) {
            newPassField.setEditable(true);
            repeatNewPassField.setEditable(true);
            updatePassButton.setDisable(false);
        }
    }

    @FXML
    void updatePassword(MouseEvent event) {
        UserDAO userDAO = new UserDAO();
        userDAO.updateInputData(newPassField.getText(), recoveryLoginField.getText());
        AuthController.recoverStage.close();
        MainController.authStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
