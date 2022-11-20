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
import javafx.scene.image.Image;
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
import ru.tolstikhin.entities.Schedule;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private final String DEF_NUM_OF_PAGE = "1";

    private final String DEF_PRICE_TABLEAU_STYLE = "-fx-background-color:  #f7f7f7; -fx-cursor: hand";
    private final String NEW_PRICE_TABLEAU_STYLE = "-fx-background-color: #eaeaea; -fx-cursor: hand";
    public static Stage authStage = null;
    public static Stage accountStage = null;
    private static int userId;

    private LinkedList<Pane> listOfPanes;

    private List<Schedule> scheduleList;

    private ObservableList<Node> objectsOfPane1;
    private ObservableList<Node> objectsOfPane2;
    private ObservableList<Node> objectsOfPane3;

    private LinkedList<ObservableList<Node>> linkedList;
    public LinkedList<Schedule> currShowedSchedules = new LinkedList<>();
    private static Schedule currSchedule;

    private static Stage seatSelectionStage;

    public static int getUserId() {
        return userId;
    }

    public static Stage getSeatSelectionStage() {
        return seatSelectionStage;
    }

    public static Schedule getCurrSchedule() {
        return currSchedule;
    }

    public static void setCurrSchedule(Schedule currSchedule) {
        MainController.currSchedule = currSchedule;
    }

    @FXML
    private TextField cityFromField;

    @FXML
    private TextField cityToField;

    @FXML
    private DatePicker departureDate;

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
    private AnchorPane priceTableau1;

    @FXML
    private AnchorPane priceTableau2;

    @FXML
    private AnchorPane priceTableau3;

    @FXML
    private ImageView rightArrow;

    @FXML
    private Pane routePanel1;

    @FXML
    private Pane routePanel2;

    @FXML
    private Pane routePanel3;

    @FXML
    void showAuthorizationWindow(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/authorization.fxml")));
        Stage authorizationWindow = new Stage();
        try {
            authorizationWindow.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        authorizationWindow.getIcons().add(new Image("images/railway_icon.png"));
        authorizationWindow.setTitle("Вход");
        authorizationWindow.initModality(Modality.WINDOW_MODAL);
        authorizationWindow.initOwner(MainApp.primaryStage);
        authorizationWindow.show();
        authorizationWindow.setResizable(false);

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

        accountWindow.getIcons().add(new Image("images/railway_icon.png"));
        accountWindow.setTitle("Профиль пользователя");
        accountWindow.initModality(Modality.WINDOW_MODAL);
        accountWindow.initOwner(MainApp.primaryStage);
        accountWindow.show();

        accountStage = accountWindow;
    }

    @FXML
    void showTicketBuyWindow(MouseEvent event) {
        showTicketByWindowMethod();
    }

    public void showTicketByWindowMethod() {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/seatSelection.fxml")));
        Stage seatSelectionWindow = new Stage();
        try {
            seatSelectionWindow.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        seatSelectionWindow.getIcons().add(new Image("images/railway_icon.png"));
        seatSelectionWindow.setTitle("Выбор мест");
        seatSelectionWindow.initModality(Modality.WINDOW_MODAL);
        seatSelectionWindow.initOwner(MainApp.primaryStage);
        seatSelectionWindow.show();

        seatSelectionStage = seatSelectionWindow;
    }

    @FXML
    void showRoutes(MouseEvent event) {
        ScheduleDAO scheduleDAO = new ScheduleDAO();
        scheduleList = scheduleDAO.selectSchedules(cityFromField.getText(), cityToField.getText(), departureDate.getValue());

        // Устанавливаем текущий номер страницы как 1, так как метод вызван первый раз нажатием кнопки "найти"
        numOfPage.setText(DEF_NUM_OF_PAGE);

        showRoutsMethod(0, getIntervalSize());
    }

    @FXML
    void showNextPage(MouseEvent event) {
        int interval = scheduleList.size() - (scheduleList.size() - 3 * Integer.parseInt(numOfPage.getText()));
        int intervalSize;
        if (scheduleList.size() - 3 * Integer.parseInt(numOfPage.getText()) > 3) {
            intervalSize = 3;
        } else {
            intervalSize = scheduleList.size() - 3 * Integer.parseInt(numOfPage.getText());
        }
        numOfPage.setText(String.valueOf(Integer.parseInt(numOfPage.getText()) + 1));

        priceTableau1.setVisible(false);
        priceTableau2.setVisible(false);
        priceTableau3.setVisible(false);
        showRoutsMethod(interval, intervalSize);
    }

    @FXML
    void showPreviousPage(MouseEvent event) {
        numOfPage.setText(String.valueOf(Integer.parseInt(numOfPage.getText()) - 1));
        int interval = scheduleList.size() - (scheduleList.size() - 3 * (Integer.parseInt(numOfPage.getText()) - 1));
        int intervalSize = 3;

        priceTableau1.setVisible(false);
        priceTableau2.setVisible(false);
        priceTableau3.setVisible(false);
        showRoutsMethod(interval, intervalSize);
    }

    @FXML
    void changeFirstPaneColor(MouseEvent event) {
        routePanel1.setStyle(NEW_PRICE_TABLEAU_STYLE);
        setCurrSchedule(currShowedSchedules.get(0));
    }

    @FXML
    void changeSecondPaneColor(MouseEvent event) {
        routePanel2.setStyle(NEW_PRICE_TABLEAU_STYLE);
        setCurrSchedule(currShowedSchedules.get(1));
    }

    @FXML
    void changeThirdPaneColor(MouseEvent event) {
        routePanel3.setStyle(NEW_PRICE_TABLEAU_STYLE);
        setCurrSchedule(currShowedSchedules.get(2));
    }

    @FXML
    void changeFirstPaneDefColor(MouseEvent event) {
        routePanel1.setStyle(DEF_PRICE_TABLEAU_STYLE);
    }

    @FXML
    void changeSecondPaneDefColor(MouseEvent event) {
        routePanel2.setStyle(DEF_PRICE_TABLEAU_STYLE);
    }

    @FXML
    void changeThirdPaneDefColor(MouseEvent event) {
        routePanel3.setStyle(DEF_PRICE_TABLEAU_STYLE);
    }

    private void showRoutsMethod(int interval, int intervalSize) {
        setPaneVisibleFalse();

        if (currShowedSchedules.size() != 0) {
            currShowedSchedules.clear();
        }

        if (!scheduleList.isEmpty()) {
            int pagesNumber = getPagesNumber(scheduleList.size());
            pageNumber.setText(String.valueOf(pagesNumber));
            setRightArrowDisable();
            setLeftArrowDisable();
            Pane currentPane;
            int numOfVal = 0;
            for (int i = interval; i < intervalSize + interval; ++i) {
                currShowedSchedules.add(scheduleList.get(i));

                currentPane = listOfPanes.get(numOfVal);
                currentPane.setVisible(true);

                TrainDAO trainDAO = new TrainDAO();

                // Заполняем все поля в доступном к покупке рейсе
                ((Text) linkedList.get(numOfVal).get(0)).setText(trainDAO.selectTrainName(scheduleList.get(i).getScheduleId()));

                CityDAO cityDAO = new CityDAO();
                RouteDAO routeDAO = new RouteDAO();
                ((Text) linkedList.get(numOfVal).get(1)).setText(cityDAO.selectCityFrom((routeDAO.selectRoute(scheduleList.get(i).getScheduleId())).getStationFrom()) + " - " + cityDAO.selectCityTo((routeDAO.selectRoute(scheduleList.get(i).getScheduleId())).getStationTo()));
                ((Text) linkedList.get(numOfVal).get(2)).setText(dateToString(scheduleList.get(i).getDepartureDate().toLocalDate()));
                ((Text) linkedList.get(numOfVal).get(3)).setText(scheduleList.get(i).getDepartureTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")));
                ((Text) linkedList.get(numOfVal).get(5)).setText(dateToString(scheduleList.get(i).getArrivalDate().toLocalDate()));
                ((Text) linkedList.get(numOfVal).get(6)).setText(scheduleList.get(i).getArrivalTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")));


                StationDAO stationDAO = new StationDAO();
                ((Text) linkedList.get(numOfVal).get(7)).setText(stationDAO.selectStationFrom(routeDAO.selectRoute(scheduleList.get(i).getScheduleId()).getStationFrom()));
                ((Text) linkedList.get(numOfVal).get(8)).setText(stationDAO.selectStationTo(routeDAO.selectRoute(scheduleList.get(i).getScheduleId()).getStationTo()));

                ((Text) linkedList.get(numOfVal).get(9)).setText(calculateTravelTime(scheduleList.get(i).getDepartureDate().toLocalDate(),
                        scheduleList.get(i).getArrivalDate().toLocalDate(),
                        scheduleList.get(i).getDepartureTime().toLocalTime(),
                        scheduleList.get(i).getArrivalTime().toLocalTime()));
                ++numOfVal;
            }
            fillPriceTableau(scheduleList, interval, intervalSize);
        } else {
            for (Pane pane : listOfPanes) {
                pane.setVisible(false);
            }
        }
    }


    // Получение длины интервала количества рейсов, показанных единовременно в одном окне
    private int getIntervalSize() {
        int size = scheduleList.size();

        /* Если количество доступных рейсов больше 3-х, то из-за ограничения количества показанных
         на главном окне рейсов (3) устанавливаем значение 3*/
        if (size > 3) {
            size = 3;
        }
        return size;
    }

    private void setPaneVisibleFalse() {
        for (Pane pane : listOfPanes) {
            pane.setVisible(false);
        }
    }

    private void setRightArrowDisable() {
        if (Integer.parseInt(pageNumber.getText()) != 1
                && Integer.parseInt(numOfPage.getText()) != Integer.parseInt(pageNumber.getText())) {
            rightArrow.setDisable(false);
        } else {
            rightArrow.setDisable(true);
        }
    }

    private void setLeftArrowDisable() {
        if (Integer.parseInt(numOfPage.getText()) != 1) {
            leftArrow.setDisable(false);
        } else {
            leftArrow.setDisable(true);
        }
    }

    private int getPagesNumber(int schedulesNumber) {
        return (int) Math.ceil(schedulesNumber / 3.0);
    }

    /**
     * Заполнение табло с ценами на билеты
     *
     * @param scheduleList Список доступных к покупке рейсов
     * @param interval     Начало интервала
     * @param intervalSize Длина интервала
     */
    private void fillPriceTableau(List<Schedule> scheduleList, int interval, int intervalSize) {
        ObservableList<Node> listOfPricePanes1 = priceTableau1.getChildren();
        ObservableList<Node> listOfPricePanes2 = priceTableau2.getChildren();
        ObservableList<Node> listOfPricePanes3 = priceTableau3.getChildren();

        LinkedList<ObservableList<Node>> listLinkedList = new LinkedList<>();
        listLinkedList.add(listOfPricePanes1);
        listLinkedList.add(listOfPricePanes2);
        listLinkedList.add(listOfPricePanes3);

        priceFillingCycle(interval, intervalSize, listLinkedList, scheduleList);
    }

    private void priceFillingCycle(int interval, int intervalSize, LinkedList<ObservableList<Node>> listLinkedList,
                                   List<Schedule> scheduleList) {
        WagonDAO wagonDAO = new WagonDAO();
        WagonTypeDAO wagonTypeDAO = new WagonTypeDAO();
        ObservableList<Node> listOfTexts;

        SeatDAO seatDAO = new SeatDAO();

        DecimalFormat dF = new DecimalFormat("###,###.##");

        String wagonTypeName;
        int numOfPane = 0;
        int pricePanesNum = 0;
        for (int k = interval; k < intervalSize + interval; ++k) {
            for (int i = 0; i < wagonDAO.findWagonTypesCount(scheduleList.get(k).getTrainNumber()); ++i) {
                listOfTexts = ((Pane) listLinkedList.get(pricePanesNum).get(numOfPane)).getChildren();
                for (int j = 0; j < listOfTexts.size(); ++j) {
                    wagonTypeName = wagonTypeDAO.selectWagonsTypesNames(scheduleList.get(k).getTrainNumber()).get(i);

                    if (seatDAO.findFreeSeatsCount(scheduleList.get(k).getTrainNumber(), wagonTypeDAO.selectWagonTypeId(wagonTypeName)) != 0) {
                        if (j == 0) {
                            ((Text) listOfTexts.get(j)).setText(wagonTypeName);
                        } else if (j == 1) {
                            ((Text) listOfTexts.get(j)).setText(Long.toString(seatDAO.findFreeSeatsCount(scheduleList.get(k).getTrainNumber(), wagonTypeDAO.selectWagonTypeId(wagonTypeName))));
                        } else {
                            ((Text) listOfTexts.get(j)).setText(dF.format(seatDAO.selectSeatPrice(scheduleList.get(k).getTrainNumber(), wagonTypeDAO.selectWagonTypeId(wagonTypeName))) + " ₽");
                        }
                        listLinkedList.get(pricePanesNum).get(numOfPane).getParent().setVisible(true);
                        listLinkedList.get(pricePanesNum).get(numOfPane).setVisible(true);
                    } else {
                        listLinkedList.get(pricePanesNum).get(numOfPane).setVisible(false);
                        if (numOfPane != 0) {
                            --numOfPane;
                        }
                        break;
                    }
                }
                ++numOfPane;
            }
            numOfPane = 0;
            ++pricePanesNum;
        }
    }

    /**
     * Вычисление времени в пути в разных еденицах измерения
     * @param departureDate дата отправления
     * @param arrivalDate дата прибытия
     * @param departureTime время отправления
     * @param arrivalTime время прибытия
     * @return Время в виде строки в разных еденицах измерения
     */
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

    /**
     * Запуск базы данных
     */
    private void startDatabase() {
        Session session = HibernateUtil.openSession();
        session.close();
    }

    /**
     * Преобразование даты в нужный формат для отображения в расписании рейса
     *
     * @param date Дата поездки
     * @return Дата поездки в нужном строковом формате
     */
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

        listOfPanes = new LinkedList<>();
        listOfPanes.add(routePanel1);
        listOfPanes.add(routePanel2);
        listOfPanes.add(routePanel3);

        objectsOfPane1 = listOfPanes.get(0).getChildren();
        objectsOfPane2 = listOfPanes.get(1).getChildren();
        objectsOfPane3 = listOfPanes.get(2).getChildren();

        linkedList = new LinkedList<>();
        linkedList.add(objectsOfPane1);
        linkedList.add(objectsOfPane2);
        linkedList.add(objectsOfPane3);
    }
}