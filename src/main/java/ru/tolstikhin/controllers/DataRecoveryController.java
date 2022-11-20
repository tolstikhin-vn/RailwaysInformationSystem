package ru.tolstikhin.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import ru.tolstikhin.DAO.UserDAO;

public class DataRecoveryController {

    @FXML
    public PasswordField newPassField;

    @FXML
    public TextField recoveryLoginField;

    @FXML
    public PasswordField repeatNewPassField;

    @FXML
    public Button updatePassButton;
    @FXML
    public void checkLogin() {
        UserDAO userDAO = new UserDAO();
        if (userDAO.userExists(recoveryLoginField.getText())) {
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
}
