package ru.tolstikhin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainApp extends Application {
    public static Stage primaryStage;
    private Scene scene;
    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    @Override
    public void start(Stage newPrimaryStage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/sample.fxml")));

        Scene mainScene = new Scene(root);
        setScene(mainScene);
        newPrimaryStage.getIcons().add(new Image("images/railway_icon.png"));
        newPrimaryStage.setTitle("РЖД");
        newPrimaryStage.setScene(mainScene);
        newPrimaryStage.show();
        newPrimaryStage.setResizable(false);

        primaryStage = newPrimaryStage;
    }
    public static void main(String[] args) {
        launch(args);
    }
}