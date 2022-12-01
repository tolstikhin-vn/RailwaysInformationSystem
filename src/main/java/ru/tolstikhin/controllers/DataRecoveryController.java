package ru.tolstikhin.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import ru.tolstikhin.DAO.UserDAO;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class DataRecoveryController implements Initializable {

    private final String REGEX = "^[a-zA-Z0-9]{8,}$";
    private final String USER_NOT_FOUND_MESSAGE = "Пользователь с таким логином не зарегистирован!";
    private final String SUCCESSFUL_RECOVERY = "Пароль успешно изменён!";

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
            newPassField.setDisable(false);
            repeatNewPassField.setDisable(false);
        } else {
            showAlertWindow(USER_NOT_FOUND_MESSAGE);
            resetFields();
        }
    }

    private void showAlertWindow(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Информация");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }

    private void resetFields() {
        newPassField.setText("");
        repeatNewPassField.setText("");
        newPassField.setDisable(true);
        repeatNewPassField.setDisable(true);
        updatePassButton.setDisable(true);
    }

    private void setRecoveryLogTextFieldListener() {
        recoveryLoginField.textProperty().addListener((observable, oldValue, newValue) -> {
            resetFields();
        });
    }

    private void setNewPassTextFieldListener() {
        newPassField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!Objects.equals(newPassField.getText(), repeatNewPassField.getText()) || newPassField.getText().isEmpty()) {
                updatePassButton.setDisable(true);
            } else {
                if (newValue.matches(REGEX)) {
                    updatePassButton.setDisable(false);
                } else {
                    updatePassButton.setDisable(true);
                }
            }
        });
    }

    private void setRepeatNewPassTextFieldListener() {
        repeatNewPassField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!Objects.equals(newPassField.getText(), repeatNewPassField.getText()) || repeatNewPassField.getText().isEmpty()) {
                updatePassButton.setDisable(true);
            } else {
                if (newValue.matches(REGEX)) {
                    updatePassButton.setDisable(false);
                } else {
                    updatePassButton.setDisable(true);
                }
            }
        });
    }

    @FXML
    void updatePassword(MouseEvent event) {
        UserDAO userDAO = new UserDAO();
        userDAO.updateInputData(newPassField.getText(), recoveryLoginField.getText());
        showAlertWindow(SUCCESSFUL_RECOVERY);
        AuthController.recoverStage.close();
        MainController.authStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setRecoveryLogTextFieldListener();
        setNewPassTextFieldListener();
        setRepeatNewPassTextFieldListener();
    }
}
