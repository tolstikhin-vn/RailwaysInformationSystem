package ru.tolstikhin.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.tolstikhin.DAO.SeatDAO;
import ru.tolstikhin.DAO.WagonDAO;
import ru.tolstikhin.entities.Schedule;
import ru.tolstikhin.entities.Seat;
import ru.tolstikhin.entities.Wagon;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class SeatSelectionController implements Initializable {

    @FXML
    private ComboBox<Integer> numbOfWagonBox;

    @FXML
    private AnchorPane reservedSeats;

    @FXML
    private AnchorPane compartmentSeats;

    @FXML
    private AnchorPane svSeats;

    @FXML
    private AnchorPane seats;

    @FXML
    private Text wagonTypeText;

    @FXML
    private Pane continuePane;

    private final String FREE_SEAT_IMAGE = "/images/free_seat.png";
    private final String SEAT_IMAGE = "/images/seat.png";
    private final String SELECTED_SEAT_IMAGE = "/images/selected_seat.png";
    private final String CURSOR_HAND_STYLE = "-fx-cursor: hand";
    private final String NULL_CURSOR_HAND_STYLE = "-fx-cursor: null";

    private static int numberOfWagon; // Номер вагона
    private static int wagonNumberOnTrain; // Номер вагона в поезде

    private static int seatIndex; // Индекс выбранного места в вагоне

    private static Stage buyWindowStage; // Stage окна покупки билета

    public static int getNumberOfWagon() {
        return numberOfWagon;
    }

    public static int getWagonNumberOnTrain() {
        return wagonNumberOnTrain;
    }

    public static void setNumberOfWagon(int numberOfWagon) {
        SeatSelectionController.numberOfWagon = numberOfWagon;
    }

    public static void setWagonNumberOnTrain(int wagonNumberOnTrain) {
        SeatSelectionController.wagonNumberOnTrain = wagonNumberOnTrain;
    }

    public static Stage getBuyWindowStage() {
        return buyWindowStage;
    }

    public static int getSeatIndex() {
        return seatIndex;
    }

    @FXML
    void continuePaneOnClicked(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/buy.fxml")));
        Stage buyWindow = new Stage();
        try {
            buyWindow.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        buyWindow.getIcons().add(new Image("images/railway_icon.png"));
        buyWindow.setTitle("Покупка билета");
        buyWindow.initModality(Modality.WINDOW_MODAL);
        buyWindow.show();
        buyWindowStage = buyWindow;
    }

    private void showWagons(Schedule currSchedule) {
        WagonDAO wagonDAO = new WagonDAO();
        List<Wagon> wagonList = wagonDAO.getWagons(currSchedule.getScheduleId());
        ObservableList<Integer> wagonObservList = FXCollections.observableArrayList();

        for (Wagon wagon : wagonList) {
            wagonObservList.add(wagon.getWagonNumberOnTrain());
        }
        numbOfWagonBox.setItems(wagonObservList);
    }

    private void showSeats(int wagonNumber) {

        WagonDAO wagonDAO = new WagonDAO();
        setNumberOfWagon(wagonDAO.selectWagon(wagonNumber, MainController.getCurrSchedule().getTrainNumber()).getWagonNumber());
        setWagonNumberOnTrain(wagonNumber);

        reservedSeats.setVisible(false);
        compartmentSeats.setVisible(false);
        svSeats.setVisible(false);
        seats.setVisible(false);

        SeatDAO seatDAO = new SeatDAO();

        List<Seat> list = seatDAO.selectFreeSeats(getNumberOfWagon());
        int wagonTypeNumber = wagonDAO.selectWagon(numbOfWagonBox.getValue(), MainController.getCurrSchedule().getTrainNumber()).getWagonType();

        ObservableList<Node> listOfSeatImages = getListOfSeatImages(wagonTypeNumber);

        assert listOfSeatImages != null;
        setDefaultSeats(listOfSeatImages);
        setFreeSeatsStyle(listOfSeatImages, list);

        eventFreeSeatHandlers(listOfSeatImages);
    }

    /**
     * Получить список иконок мест для конкретного типа вагона
     * @param wagonTypeNumber тип вагона
     * @return список иконок
     */
    private ObservableList<Node> getListOfSeatImages(int wagonTypeNumber) {
        switch (wagonTypeNumber) {
            case (1) -> {
                wagonTypeText.setText("Плацкартный");
                reservedSeats.setVisible(true);
                return reservedSeats.getChildren();
            }
            case (2) -> {
                wagonTypeText.setText("Купе");
                compartmentSeats.setVisible(true);
                return compartmentSeats.getChildren();
            }
            case (3) -> {
                wagonTypeText.setText("СВ");
                svSeats.setVisible(true);
                return svSeats.getChildren();
            }
            case (4) -> {
                wagonTypeText.setText("Сидячий");
                seats.setVisible(true);
                return seats.getChildren();
            }
            default -> {
                return null;
            }
        }
    }

    /**
     * Задать стиль для иконок доступных к покупке мест
     * @param listOfSeatImages список иконок мест
     * @param list список мест
     */
    private void setFreeSeatsStyle(ObservableList<Node> listOfSeatImages, List<Seat> list) {
        for (int i = 0; i < listOfSeatImages.size(); ++i) {
            for (Seat seat : list) {
                if (seat.getSeatNumberInWagon() == (i + 1)) {
                    ((ImageView) listOfSeatImages.get(i)).setImage(new Image(FREE_SEAT_IMAGE));
                    listOfSeatImages.get(i).setStyle(CURSOR_HAND_STYLE);
                }
            }
        }
    }

    public void eventFreeSeatHandlers(ObservableList<Node> listOfSeatImages) {
        for (Node node : listOfSeatImages) {
            node.setOnMouseClicked(event -> {
                if (((ImageView) node).getImage().getUrl().contains("free_seat.png")) {
                    for (Node node1 : listOfSeatImages) {
                        if (((ImageView) node1).getImage().getUrl().contains("selected_seat.png")) {
                            ((ImageView) node1).setImage(new Image(FREE_SEAT_IMAGE));
                        }
                    }
                    ((ImageView) node).setImage(new Image(SELECTED_SEAT_IMAGE));
                    seatIndex = listOfSeatImages.indexOf(node) + 1;
                }
            });
        }
    }

    private void setDefaultSeats(ObservableList<Node> listOfSeatImages) {
        for (Node node : listOfSeatImages) {
            ((ImageView) node).setImage(new Image(SEAT_IMAGE));
            node.setStyle(NULL_CURSOR_HAND_STYLE);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Schedule currSchedule = MainController.getCurrSchedule();
        showWagons(currSchedule);
        numbOfWagonBox.setOnAction(event -> showSeats(numbOfWagonBox.getValue()));
    }
}
