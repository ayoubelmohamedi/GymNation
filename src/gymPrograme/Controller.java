package gymPrograme;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private ListView<Clients> allClientsList;


    ManagerPayment managerPayment;
    private ObservableList<Clients> clientsObservableList;

    public Controller() {
        clientsObservableList = FXCollections.observableArrayList();
        managerPayment = new ManagerPayment();

        //add to observableList
        clientsObservableList.addAll(managerPayment.getAllClients());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allClientsList.setItems(clientsObservableList);
        allClientsList.setCellFactory(clientsListView -> new ClientsListViewCell());
    }

}
