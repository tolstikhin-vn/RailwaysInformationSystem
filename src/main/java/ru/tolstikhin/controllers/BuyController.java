package ru.tolstikhin.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import ru.tolstikhin.DAO.CityDAO;
import ru.tolstikhin.DAO.PassengerDAO;
import ru.tolstikhin.DAO.RouteDAO;
import ru.tolstikhin.DAO.SeatDAO;
import ru.tolstikhin.DAO.StationDAO;
import ru.tolstikhin.DAO.TicketDAO;
import ru.tolstikhin.DAO.TrainDAO;
import ru.tolstikhin.DAO.WagonDAO;
import ru.tolstikhin.DAO.WagonTypeDAO;
import ru.tolstikhin.entities.Passenger;
import ru.tolstikhin.entities.Schedule;
import ru.tolstikhin.entities.TrainStation;

import java.util.List;

public class BuyController {
    @FXML
    private ComboBox<String> passengersListForBuying;

    @FXML
    void showPassOnClickedForBuying(MouseEvent event) {
        AccountController accountController = new AccountController();
        accountController.showPassengersList(passengersListForBuying);
    }

    @FXML
    void buyTicket(MouseEvent event) {
        TicketDAO ticketDAO = new TicketDAO();
        PassengerDAO passengerDAO = new PassengerDAO();
        List<Passenger> passengerList = passengerDAO.selectPassengers(MainController.getUserId());
        Passenger passenger = passengerList.get(passengersListForBuying.getSelectionModel().getSelectedIndex());
        TrainDAO trainDAO = new TrainDAO();
        WagonTypeDAO wagonTypeDAO = new WagonTypeDAO();
        WagonDAO wagonDAO = new WagonDAO();
        RouteDAO routeDAO = new RouteDAO();
        CityDAO cityDAO = new CityDAO();
        StationDAO stationDAO = new StationDAO();
        SeatDAO seatDAO = new SeatDAO();

        // Расписание маршрута, для которого покупается билет
        Schedule schedule = MainController.getCurrSchedule();

        ticketDAO.insertTicket(MainController.getUserId(), trainDAO.selectTrainName(schedule.getScheduleId()),
                SeatSelectionController.getNumberOfWagon(), wagonTypeDAO.selectWagonType(wagonDAO.selectWagon(SeatSelectionController.getWagonNumberOnTrain(),
                        schedule.getTrainNumber()).getWagonType()).getTypeName(),
                SeatSelectionController.getSeatIndex(), passenger.getName(), passenger.getSurname(), passenger.getPatronymic(), passenger.getPassportData(),
                passenger.getBirthdate(), schedule.getDepartureDate(), schedule.getDepartureTime(),
                schedule.getArrivalDate(), schedule.getArrivalTime(),
                cityDAO.selectCityFrom(routeDAO.selectRoute(schedule.getScheduleId()).getStationFrom()),
                stationDAO.selectStationFrom(routeDAO.selectRoute(schedule.getScheduleId()).getStationFrom()),
                cityDAO.selectCityTo(routeDAO.selectRoute(schedule.getScheduleId()).getStationTo()),
                stationDAO.selectStationTo(routeDAO.selectRoute(schedule.getScheduleId()).getStationTo()),
                seatDAO.selectSeat(SeatSelectionController.getSeatIndex(), SeatSelectionController.getNumberOfWagon()).getSeatPrice());

        seatDAO.updateSeatBooked(seatDAO.selectSeat(SeatSelectionController.getSeatIndex(), SeatSelectionController.getNumberOfWagon()).getSeatNumber(), true);

        showAlertWindow();

        SeatSelectionController.getBuyWindowStage().close();
        MainController.getSeatSelectionStage().close();
        MainController mainController = new MainController();
        mainController.showTicketByWindowMethod();
    }

    private void showAlertWindow() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Информация");
        alert.setHeaderText(null);
        alert.setContentText("Билет оформлен");

        alert.showAndWait();
    }
}
