package ru.tolstikhin.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import ru.tolstikhin.DAO.PassengerDAO;
import ru.tolstikhin.DAO.SeatDAO;
import ru.tolstikhin.DAO.TicketDAO;
import ru.tolstikhin.DAO.UserDAO;
import ru.tolstikhin.MainApp;
import ru.tolstikhin.entities.Passenger;
import ru.tolstikhin.entities.Ticket;
import ru.tolstikhin.entities.User;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class AccountController extends MainApp implements Initializable {

    public final int ID_NON_EXISTENT_USER = 0;
    private final String FX_BACKGROUND_COLOR_RED = "-fx-background-color: red";
    private final String FX_BACKGROUND_NO_COLOR = "-fx-background-color: null";

    private final String DEF_MENU_PANE_STYLE = "-fx-background-color:  #f7f7f7; -fx-cursor: hand";
    private final String NEW_MENU_PANE_STYLE = "-fx-background-color: #eaeaea; -fx-cursor: hand";
    private final String PASSENGERS_TEXT = "Пассажиры";
    private final String SUCCESSFUL_SAVING = "Данные успешно сохранены!";
    private final String NOT_SUCCESSFUL_SAVING = "Данные не сохранены! Не заполнены или заполнены некорректно все обязательные поля.";
    private final String INCORRECT_EMAIL = "Адрес электронной почты некорректный!";
    private final String FILL_IN_DATA = "Чтобы добавлять пассажиров необходимо заполнить раздел \"Основная информация\" в профиле!";
    private final String EMPTY_DATA_FIELDS = "Все поля должны быть заполнены!";
    
    private final String TICKET_RETURNED = "Билет возвращен!";

    private final String PASSENGER_ADDED = "Пассажир добавлен!";
    private final String PASSENGER_NOT_ADDED = "Пассажир не добавлен! Заполните все необходимые данные корректно.";

    private final String EMAIL_REGEX = "^(?=(.{1,64}@))(?=(.{6,255}$))((([A-Za-zА-Яа-яЁё0-9]+)[-._]?" +
            "([A-Za-z0-9А-Яа-яЁё]+)?)@(([A-Za-zА-Яа-яЁё][A-Za-zА-Яа-яЁё\\\\-_]*.)+([A-Za-zА-Яа-яЁё0-9]{2,})" +
            "|(((25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9][0-9]|[0-9]).){3}(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9][0-9]|[0-9]))))$";

    private final String PASSPORT_DATA_REGEX = "^[0-9]{4} [0-9]{6}$";

    private int userId;

    private List<Ticket> listOfTickets;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @FXML
    private Button addPassenger;

    @FXML
    private Button addPassengerButton;

    @FXML
    private Button returnTicketButtonByPass;

    @FXML
    private Text arrivalDateText;

    @FXML
    private Text arrivalTimeText;

    @FXML
    private Text departureDateText;

    @FXML
    private Text departureTimeText;

    @FXML
    private Pane femaleGender;

    @FXML
    private Pane infAboutPassenger;

    @FXML
    private Pane maleGender;

    @FXML
    private TextField myEmailField;

    @FXML
    private TextField myLoginField;

    @FXML
    private TextField myNameField;

    @FXML
    private TextField mySurnameField;

    @FXML
    private Text orderNumber;

    @FXML
    private Text ticketPrice;

    @FXML
    private Pane orders;

    @FXML
    private ListView<String> ordersListView;

    @FXML
    private Pane ordersPane;

    @FXML
    private Pane adminPane;

    @FXML
    private DatePicker passBirthday;

    @FXML
    private Pane passFemaleGender;

    @FXML
    private Pane passMaleGender;

    @FXML
    private TextField passName;

    @FXML
    private TextField passPassportData;

    @FXML
    private TextField passPatronymic;

    @FXML
    private TextField passSurname;

    @FXML
    private Pane passengers;

    @FXML
    private ComboBox<String> passengersList;

    @FXML
    private Pane passengersPane;

    @FXML
    private Pane profile;

    @FXML
    private Pane profilePane;

    @FXML
    private Text routeNameText;

    @FXML
    private Pane orderInformation;

    @FXML
    private Button returnTicketButton;

    @FXML
    private TextField ticketNumberTextField;

    @FXML
    private Button savePassenger;

    @FXML
    private Text stationFromText;

    @FXML
    private Text stationToText;

    @FXML
    private Text trainNameText;

    @FXML
    private Text travelTimeText;

    @FXML
    void chooseFemaleGender(MouseEvent event) {
        maleGender.setStyle(FX_BACKGROUND_NO_COLOR);
        femaleGender.setStyle(FX_BACKGROUND_COLOR_RED);

    }

    @FXML
    void chooseMaleGender(MouseEvent event) {
        femaleGender.setStyle(FX_BACKGROUND_NO_COLOR);
        maleGender.setStyle(FX_BACKGROUND_COLOR_RED);
    }

    @FXML
    void choosePassFemaleGender(MouseEvent event) {
        passMaleGender.setStyle(FX_BACKGROUND_NO_COLOR);
        passFemaleGender.setStyle(FX_BACKGROUND_COLOR_RED);
    }

    @FXML
    void choosePassMaleGender(MouseEvent event) {
        passFemaleGender.setStyle(FX_BACKGROUND_NO_COLOR);
        passMaleGender.setStyle(FX_BACKGROUND_COLOR_RED);
    }

    @FXML
    void logOut(MouseEvent event) throws IOException {
        AuthController.setUserId(ID_NON_EXISTENT_USER);
        start(MainApp.primaryStage);
        MainController.accountStage.close();
    }

    @FXML
    void saveData(MouseEvent event) {
        if (!myNameField.getText().isBlank()
                && !mySurnameField.getText().isBlank()
                && !myEmailField.getText().isBlank()
                && (maleGender.getStyle().equals(FX_BACKGROUND_COLOR_RED) || femaleGender.getStyle().equals(FX_BACKGROUND_COLOR_RED))) {
            if (myEmailField.getText().matches(EMAIL_REGEX)) {
                UserDAO userDAO = new UserDAO();
                userDAO.insertData(getUserId(),
                        myNameField.getText(),
                        mySurnameField.getText(),
                        myEmailField.getText(),
                        getGender(maleGender, femaleGender));
                showAlertWindow(SUCCESSFUL_SAVING);
            } else {
                showAlertWindow(INCORRECT_EMAIL);
            }
        } else {
            showAlertWindow(EMPTY_DATA_FIELDS);
        }
    }

    @FXML
    void showPassengers(MouseEvent event) {
        UserDAO userDAO = new UserDAO();
        if (userDAO.selectUser().getName() != null
                && userDAO.selectUser().getSurname() != null
                && userDAO.selectUser().getEmail() != null
                && userDAO.selectUser().getGender() != null) {
            returnTicketButtonByPass.setDisable(true);
            profilePane.setVisible(false);
            infAboutPassenger.setVisible(false);
            ordersPane.setVisible(false);
            passengersPane.setVisible(true);
            if (!passengersList.getSelectionModel().isEmpty()) {
                passengersList.getSelectionModel().clearSelection();
                passengersList.setPromptText(PASSENGERS_TEXT);
            }
        } else {
            showAlertWindow(FILL_IN_DATA);
        }
    }

    @FXML
    void showProfile(MouseEvent event) {
        ordersListView.getSelectionModel().clearSelection();
        orderInformation.setVisible(false);
        returnTicketButtonByPass.setDisable(true);
        showProfileMethod();

    }

    @FXML
    void showOrders(MouseEvent event) {
        ordersListView.getSelectionModel().clearSelection();
        orderInformation.setVisible(false);
        profilePane.setVisible(false);
        passengersPane.setVisible(false);
        ordersPane.setVisible(true);
        returnTicketButtonByPass.setDisable(true);
        showOrdersList();
        eventUpdateHandlers();
    }


    @FXML
    void changeOrdersPaneColor(MouseEvent event) {
        changeBackgroundColor(orders);
    }

    @FXML
    void changeProfilePaneColor(MouseEvent event) {
        changeBackgroundColor(profile);
    }

    @FXML
    void changePassengersPaneColor(MouseEvent event) {
        changeBackgroundColor(passengers);
    }

    @FXML
    void changeDefOrdersPaneColor(MouseEvent event) {
        changeDefBackgroundColor(orders);
    }

    @FXML
    void changeDefProfilePaneColor(MouseEvent event) {
        changeDefBackgroundColor(profile);
    }

    @FXML
    void changeDefPassengersPaneColor(MouseEvent event) {
        changeDefBackgroundColor(passengers);
    }

    private void changeBackgroundColor(Pane currentPane) {
        currentPane.setStyle(NEW_MENU_PANE_STYLE);
    }

    private void changeDefBackgroundColor(Pane currentPane) {
        currentPane.setStyle(DEF_MENU_PANE_STYLE);
    }

    private void showOrdersList() {
        ObservableList<String> orders = FXCollections.observableArrayList();
        TicketDAO ticketDAO = new TicketDAO();
        listOfTickets = ticketDAO.selectListOfTickets(MainController.getUserId());
        for (Ticket ticket : listOfTickets) {
            orders.add(ticket.getTicketNumber());
        }
        ordersListView.setItems(orders);
    }

    private void eventUpdateHandlers() {
        if (!ordersListView.getItems().isEmpty()) {
            ordersListView.setOnMouseClicked(event -> {
                orderInformation.setVisible(true);
                returnTicketButtonByPass.setDisable(true);
                MainController mainController = new MainController();
                Ticket ticket = listOfTickets.get(ordersListView.getSelectionModel().getSelectedIndex());
                orderNumber.setText(ticket.getTicketNumber());
                ticketPrice.setText(ticket.getPrice() + " Р");
                trainNameText.setText(ticket.getTrainName());
                routeNameText.setText(ticket.getCityFrom() + " - " + ticket.getCityTo());
                departureDateText.setText(ticket.getDepartureDate().toString());
                departureTimeText.setText(ticket.getDepartureTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")));
                arrivalDateText.setText(ticket.getArrivalDate().toString());
                arrivalTimeText.setText(ticket.getArrivalTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")));
                stationFromText.setText(ticket.getStationFrom());
                stationToText.setText(ticket.getStationTo());
                stationToText.setText(ticket.getStationTo());
                travelTimeText.setText(mainController.calculateTravelTime(ticket.getDepartureDate().toLocalDate(),
                        ticket.getArrivalDate().toLocalDate(),
                        ticket.getDepartureTime().toLocalTime(),
                        ticket.getArrivalTime().toLocalTime()));
                setReturnButtonDisableFalse(ticket);
            });
        }
    }

    private void setReturnButtonDisableFalse(Ticket ticket) {
        Date date = new Date();
        if (ticket.getDepartureDate().after(date)) {
            returnTicketButtonByPass.setDisable(false);
        }
    }

    @FXML
    void showPassengersOnClicked(MouseEvent event) {
        showPassengersList(passengersList);
    }

    @FXML
    void showAddingPassenger(MouseEvent event) {
        passengersList.getSelectionModel().clearSelection();
        passengersList.setPromptText(PASSENGERS_TEXT);
        infAboutPassenger.setVisible(true);
        addPassenger.setVisible(true);
        savePassenger.setVisible(false);
    }

    @FXML
    void savePassengerData(MouseEvent event) {
        if (isDataFull()) {
            PassengerDAO passengerDAO = new PassengerDAO();
            List<Passenger> passengerList = passengerDAO.selectPassengers(MainController.getUserId());
            Passenger passenger = passengerList.get(passengersList.getSelectionModel().getSelectedIndex());

            passengerDAO.updateData(passenger.getPassengerId(),
                    passenger.getName(),
                    passenger.getSurname(),
                    passenger.getPatronymic(),
                    passenger.getBirthdate(),
                    passenger.getPassportData(),
                    passenger.getGender());
            showAlertWindow(SUCCESSFUL_SAVING);
        } else {
            showAlertWindow(NOT_SUCCESSFUL_SAVING);
        }
    }

    @FXML
    void addNewPassenger() {
        if (isDataFull()) {
            PassengerDAO passengerDAO = new PassengerDAO();
            passengerDAO.insertData(MainController.getUserId(),
                    passName.getText(),
                    passSurname.getText(),
                    passPatronymic.getText(),
                    java.sql.Date.valueOf(passBirthday.getValue()),
                    passPassportData.getText(),
                    getGender(passMaleGender, passFemaleGender));
            showAlertWindow(PASSENGER_ADDED);
        } else {
            showAlertWindow(PASSENGER_NOT_ADDED);
        }
    }

    @FXML
    void returnTicket(MouseEvent event) {
        TicketDAO ticketDAO = new TicketDAO();
        SeatDAO seatDAO = new SeatDAO();
        Ticket currentTicket = getTicket();
        seatDAO.updateSeatBooked(seatDAO.selectSeat(currentTicket.getSeatNumber(), currentTicket.getWagonNumber()).getSeatNumber(), false);
        ticketDAO.deleteTicket(currentTicket.getTicketNumber());
        showOrdersList();
        eventUpdateHandlers();
        orderInformation.setVisible(false);
        returnTicketButtonByPass.setDisable(true);
    }

    private Ticket getTicket() {
        return listOfTickets.get(ordersListView.getSelectionModel().getSelectedIndex());
    }

    @FXML
    void findTicket(MouseEvent event) {
        TicketDAO ticketDAO = new TicketDAO();
        returnTicketButton.setDisable(!ticketDAO.isTicketExist(Integer.parseInt(ticketNumberTextField.getText())));
    }

    @FXML
    void returnTicketByAdmin(MouseEvent event) {
        TicketDAO ticketDAO = new TicketDAO();
        SeatDAO seatDAO = new SeatDAO();
        Ticket currentTicket = ticketDAO.selectTicket(Integer.parseInt(ticketNumberTextField.getText()));
        seatDAO.updateSeatBooked(seatDAO.selectSeat(currentTicket.getSeatNumber(), currentTicket.getWagonNumber()).getSeatNumber(), false);
        ticketDAO.deleteTicket(currentTicket.getTicketNumber());
        ticketNumberTextField.setText("");
        returnTicketButton.setDisable(true);
        showAlertWindow(TICKET_RETURNED);
    }

    private boolean isDataFull() {
        if (!passName.getText().isBlank()
                && !passSurname.getText().isBlank()
                && passBirthday.getValue() != null
            && passPassportData.getText().matches(PASSPORT_DATA_REGEX)
            && getGender(passMaleGender, passFemaleGender) != null) {
            return true;
        }
        return false;
    }

    private void showProfileMethod() {
        profile.setVisible(true);
        orders.setVisible(true);
        passengers.setVisible(true);
        passengersPane.setVisible(false);
        ordersPane.setVisible(false);
        adminPane.setVisible(false);
        profilePane.setVisible(true);
    }

    /**
     * Показать информационное окно
     */
    private void showAlertWindow(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Информация");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }

    /**
     * Получить ообозначение пола пользователя
     *
     * @param maleGenderPane   Pane с мужским полом
     * @param femaleGenderPane Pane с женским полом
     * @return обозначение пола
     */
    private String getGender(Pane maleGenderPane, Pane femaleGenderPane) {
        // Если стилистически выделен maleGenderPane, пол мужжской, иначе, если femaleGenderPane, - женский
        if (Objects.equals(maleGenderPane.getStyle(), FX_BACKGROUND_COLOR_RED)) {
            return "М";
        } else if (Objects.equals(femaleGenderPane.getStyle(), FX_BACKGROUND_COLOR_RED)) {
            return "Ж";
        }
        return null;
    }

    /**
     * Заполнить персональные данные пользователя в профиле
     */
    private void fillOtherData() {
        UserDAO userDAO = new UserDAO();
        User user = userDAO.selectUser();
        mySurnameField.setText(user.getSurname());
        myNameField.setText(user.getName());
        myEmailField.setText(user.getEmail());
        if (Objects.equals(user.getGender(), "М")) {
            maleGender.setStyle(FX_BACKGROUND_COLOR_RED);
        } else if (Objects.equals(user.getGender(), "Ж")) {
            femaleGender.setStyle(FX_BACKGROUND_COLOR_RED);
        }
    }

    /**
     * Заполнить поле логина в профиле пользователя
     */
    private void fillLogin() {
        UserDAO userDAO = new UserDAO();
        setUserId(MainController.getUserId());
        myLoginField.setText(userDAO.selectLoginName(getUserId()));
    }

    /**
     * Заполнить ComboBox пассажиров пассажирами, связанными с авторизированным аккаунтом
     *
     * @param passList ComboBox пассажиров
     */
    public void showPassengersList(ComboBox<String> passList) {
        ObservableList<String> passengerObservList = FXCollections.observableArrayList();
        PassengerDAO passengerDAO = new PassengerDAO();
        List<Passenger> passengerList = passengerDAO.selectPassengers(MainController.getUserId());
        for (Passenger passenger : passengerList) {
            passengerObservList.add(passenger.getSurname() + " " + passenger.getName() + " " + passenger.getPatronymic());
        }
        // Добавляем в ComboBox результирующий список
        passList.setItems(passengerObservList);
    }

    /**
     * @param passengerIndex индекс выбранного пассажира в ComboBox
     */
    private void showInfAboutPassenger(int passengerIndex) {
        setDefaultGenderPaneStyle();

        // Если пассажир выбран, заполняем поля его данными, иначе - очищаем поля
        if (!passengersList.getSelectionModel().isEmpty()) {
            addPassenger.setVisible(false);
            savePassenger.setVisible(true);
            PassengerDAO passengerDAO = new PassengerDAO();
            List<Passenger> passengerList = passengerDAO.selectPassengers(MainController.getUserId());
            infAboutPassenger.setVisible(true);
            Passenger passenger = passengerList.get(passengerIndex);
            passSurname.setText(passenger.getSurname());
            passName.setText(passenger.getName());
            passPatronymic.setText(passenger.getPatronymic());
            passBirthday.setValue(passenger.getBirthdate().toLocalDate());
            passPassportData.setText(passenger.getPassportData());

            if (Objects.equals(passenger.getGender(), "М")) {
                passMaleGender.setStyle(FX_BACKGROUND_COLOR_RED);
            } else if (Objects.equals(passenger.getGender(), "Ж")) {
                passFemaleGender.setStyle(FX_BACKGROUND_COLOR_RED);
            }
        } else {
            passSurname.setText("");
            passName.setText("");
            passPatronymic.setText("");
            passBirthday.setValue(null);
            passPassportData.setText("");
        }
    }

    private void setDefaultGenderPaneStyle() {
        passMaleGender.setStyle(FX_BACKGROUND_NO_COLOR);
        passFemaleGender.setStyle(FX_BACKGROUND_NO_COLOR);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UserDAO userDAO = new UserDAO();
        if (userDAO.selectUser().getRole().getId() == 2) {
            // Навесить обработчики событий кликов мыши по элементам выпадающего списка пассажиров
            passengersList.setOnAction(event -> showInfAboutPassenger(passengersList.getSelectionModel().getSelectedIndex()));
            showProfileMethod();
            fillLogin();
            fillOtherData();
        } else if (userDAO.selectUser().getRole().getId() == 1) {
            profile.setVisible(false);
            orders.setVisible(false);
            passengers.setVisible(false);
            profilePane.setVisible(false);
            adminPane.setVisible(true);
        }
    }
}
