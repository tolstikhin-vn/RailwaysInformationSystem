package ru.tolstikhin.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.hibernate.Session;
import ru.tolstikhin.DAO.CityDAO;
import ru.tolstikhin.DAO.RouteDAO;
import ru.tolstikhin.DAO.ScheduleDAO;
import ru.tolstikhin.DAO.SeatDAO;
import ru.tolstikhin.DAO.StationDAO;
import ru.tolstikhin.DAO.TrainDAO;
import ru.tolstikhin.DAO.UserDAO;
import ru.tolstikhin.DAO.WagonDAO;
import ru.tolstikhin.DAO.WagonTypeDAO;
import ru.tolstikhin.HibernateUtil;
import ru.tolstikhin.MainApp;
import ru.tolstikhin.entities.Route;
import ru.tolstikhin.entities.Schedule;
import ru.tolstikhin.entities.Wagon;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    public static Stage authStage = null;
    public static Stage accountStage = null;

    private static int userId;

    public static int getUserId() {
        return userId;
    }

    @FXML
    private Text arrivalDateText;

    @FXML
    private Text arrivalTimeText;

    @FXML
    private TextField cityFromField;

    @FXML
    private TextField cityToField;

    @FXML
    private DatePicker departureDate;

    @FXML
    private Text departureDateText;

    @FXML
    private Text departureTimeText;

    @FXML
    private ImageView leftArrow;

    @FXML
    private Text logInText;

    @FXML
    private Text numOfPage;

    @FXML
    private Text pageNumber;

    @FXML
    private Text personalAccount;

    @FXML
    private ImageView rightArrow;

    @FXML
    private Text routeNameText;

    @FXML
    private Pane routePanel1;

    @FXML
    private Text stationFromText;

    @FXML
    private Text stationToText;

    @FXML
    private Text trainNameText;

    @FXML
    private Text travelTimeText;

    @FXML
    private Text freeSeatsText1;

    @FXML
    private Text freeSeatsText2;

    @FXML
    private Text freeSeatsText3;
    @FXML
    private Pane pricePane1;

    @FXML
    private Pane pricePane2;

    @FXML
    private Pane pricePane3;

    @FXML
    private AnchorPane priceTableau1;

    @FXML
    private Text priceText1;

    @FXML
    private Text priceText2;

    @FXML
    private Text priceText3;

    @FXML
    private Text wagonTypeText1;

    @FXML
    private Text wagonTypeText2;

    @FXML
    private Text wagonTypeText3;

    @FXML
    void showAuthorizationWindow(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/authorization.fxml")));
        Stage authorizationWindow = new Stage();
        try {
            authorizationWindow.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        authorizationWindow.setTitle("Вход");
        authorizationWindow.initModality(Modality.WINDOW_MODAL);
        authorizationWindow.initOwner(MainApp.primaryStage);
        authorizationWindow.show();

        authStage = authorizationWindow;
    }

    @FXML
    void showAccountWindow(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/account.fxml")));
        Stage accountWindow = new Stage();
        try {
            accountWindow.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        accountWindow.setTitle("Профиль пользователя");
        accountWindow.initModality(Modality.WINDOW_MODAL);
        accountWindow.initOwner(MainApp.primaryStage);
        accountWindow.show();

        accountStage = accountWindow;
    }

    @FXML
    void showRoutes(MouseEvent event) {
        ScheduleDAO scheduleDAO = new ScheduleDAO();
        List<Schedule> scheduleList = scheduleDAO.selectSchedules(cityFromField.getText(), cityToField.getText(), departureDate.getValue());
        if (!scheduleList.isEmpty()) {
            routePanel1.setVisible(true);

            TrainDAO trainDAO = new TrainDAO();
            RouteDAO routeDAO = new RouteDAO();
            trainNameText.setText(trainDAO.selectTrainName(routeDAO.selectRoute(scheduleList.get(0).getScheduleId()).getRouteId()));

            CityDAO cityDAO = new CityDAO();
            routeNameText.setText(cityDAO.selectCityFrom((routeDAO.selectRoute(scheduleList.get(0).getScheduleId())).getStationFrom()) + " - " + cityDAO.selectCityTo((routeDAO.selectRoute(scheduleList.get(0).getScheduleId())).getStationTo()));
            departureDateText.setText(dateToString(scheduleList.get(0).getDepartureDate().toLocalDate()));
            arrivalDateText.setText(dateToString(scheduleList.get(0).getArrivalDate().toLocalDate()));
            departureTimeText.setText(scheduleList.get(0).getDepartureTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")));
            arrivalTimeText.setText(scheduleList.get(0).getArrivalTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")));

            travelTimeText.setText(calculateTravelTime(scheduleList.get(0).getDepartureDate().toLocalDate(),
                    scheduleList.get(0).getArrivalDate().toLocalDate(),
                    scheduleList.get(0).getDepartureTime().toLocalTime(),
                    scheduleList.get(0).getArrivalTime().toLocalTime()));

            StationDAO stationDAO = new StationDAO();
            stationFromText.setText(stationDAO.selectStationFrom(routeDAO.selectRoute(scheduleList.get(0).getScheduleId()).getStationFrom()));
            stationToText.setText(stationDAO.selectStationTo(routeDAO.selectRoute(scheduleList.get(0).getScheduleId()).getStationTo()));

            fillPriceTableau(scheduleList);
        } else {
            routePanel1.setVisible(false);
        }
    }

    private void fillPriceTableau(List<Schedule> scheduleList) {
        WagonDAO wagonDAO = new WagonDAO();
        WagonTypeDAO wagonTypeDAO = new WagonTypeDAO();
        Long wagonTypesCount = wagonDAO.findWagonTypesCount(scheduleList.get(0).getTrainNumber());
//        List<Wagon> wagonList = wagonDAO.findWagons(scheduleList.get(0).getTrain());

//        int wagonTypesCount = 0;
//        List<>
//        for (Wagon wagon : wagonList) {
//            wagon.
//        }

        ObservableList<Node> listOfPricePanes = priceTableau1.getChildren();
        ObservableList<Node> listOfTexts;

        SeatDAO seatDAO = new SeatDAO();

        DecimalFormat dF = new DecimalFormat("###,###.##");

        String wagonTypeName;
        int numOfPane = 0;
        for (int i = 0; i < wagonTypesCount; ++i) {
            listOfTexts = ((Pane) listOfPricePanes.get(numOfPane)).getChildren();
            for (int j = 0; j < listOfTexts.size(); ++j) {
                wagonTypeName = wagonTypeDAO.selectWagonsTypesNames(scheduleList.get(0).getTrainNumber()).get(i);

                if (seatDAO.findFreeSeatsCount(scheduleList.get(0).getTrainNumber(), wagonTypeDAO.selectWagonTypeId(wagonTypeName)) != 0) {
                    if (j == 0) {
                        ((Text) listOfTexts.get(j)).setText(wagonTypeName);
                    } else if (j == 1) {
                        ((Text) listOfTexts.get(j)).setText(Long.toString(seatDAO.findFreeSeatsCount(scheduleList.get(0).getTrainNumber(), wagonTypeDAO.selectWagonTypeId(wagonTypeName))));
                    } else {
                        ((Text) listOfTexts.get(j)).setText(dF.format(seatDAO.selectSeatPrice(scheduleList.get(0).getTrainNumber(), wagonTypeDAO.selectWagonTypeId(wagonTypeName))) + " ₽");
                    }
                } else {
                    if (numOfPane != 0) {
                        --numOfPane;
                    }
                    break;
                }
            }
            listOfPricePanes.get(numOfPane).setVisible(true);
            ++numOfPane;
        }
    }

    private String calculateTravelTime(LocalDate departureDate, LocalDate arrivalDate, LocalTime departureTime, LocalTime arrivalTime) {
        LocalDateTime localDateTimeFrom = LocalDateTime.of(departureDate, departureTime);
        LocalDateTime localDateTimeTo = LocalDateTime.of(arrivalDate, arrivalTime);
        long mills = Duration.between(localDateTimeFrom, localDateTimeTo).toMillis();
        if (((int) ((mills / (1000 * 60 * 60 * 24)) % 30)) != 0) {
            return (int) ((mills / (1000 * 60 * 60 * 24)) % 30) + " д " + (int) ((mills / (1000 * 60 * 60)) % 24) + " ч " + (int) ((mills / (1000 * 60)) % 60) + " мин";
        } else {
            if ((int) ((mills / (1000 * 60 * 60)) % 24) != 0) {
                return (int) ((mills / (1000 * 60 * 60)) % 24) + " ч " + (int) ((mills / (1000 * 60)) % 60) + " мин";
            } else {
                return (int) ((mills / (1000 * 60)) % 60) + " мин";
            }
        }
    }

    /**
     * Сделать прошедшие дни относительно текущего неактивными для выбора
     */
    private void disablePastDates() {
        departureDate.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0);
            }
        });
    }

    /**
     * Изменить logInText на personalAccount, если пользователя вошел в аккаунт, для возможности перехода в него
     */
    private void setPersonalAccount() {
        userId = AuthController.getUserId();
        if (userId != 0) {
            UserDAO userDAO = new UserDAO();

            logInText.setVisible(false);
            logInText.setDisable(true);

            personalAccount.setVisible(true);
            personalAccount.setDisable(false);

            personalAccount.setText(userDAO.selectLoginName(userId));
        } else {
            logInText.setVisible(true);
            logInText.setDisable(false);

            personalAccount.setVisible(false);
            personalAccount.setDisable(true);
        }
    }

    private void startDatabase() {
        Session session = HibernateUtil.openSession();
        session.close();
    }

    public String dateToString(LocalDate date) {
        return date.getDayOfMonth() + " " + getRuMonthName(date.getMonth().getValue()) + "., " + getRuDayName(date.getDayOfWeek().getValue());
    }

    private String getRuMonthName(int month) {
        return switch (month) {
            case (1) -> "янв";
            case (2) -> "фев";
            case (3) -> "мар";
            case (4) -> "апр";
            case (5) -> "мая";
            case (6) -> "июн";
            case (7) -> "июл";
            case (8) -> "авг";
            case (9) -> "сен";
            case (10) -> "окт";
            case (11) -> "ноя";
            case (12) -> "дек";
            default -> null;
        };
    }

    public String getRuDayName(int day) {
        return switch (day) {
            case (1) -> "пн";
            case (2) -> "вт";
            case (3) -> "ср";
            case (4) -> "чт";
            case (5) -> "пт";
            case (6) -> "сб";
            case (7) -> "вс";
            default -> null;
        };
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startDatabase();
        setPersonalAccount();
        disablePastDates();
    }
}