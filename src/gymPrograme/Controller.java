package gymPrograme;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    public ListView<Clients> allClientsList;

    ManagerPayment managerPayment;
    public static ObservableList<Clients> clientsObservableList;

    public Controller() {
        clientsObservableList = FXCollections.observableArrayList();
        managerPayment = new ManagerPayment();

        //add to observableList
        if (managerPayment.getAllClients() != null) {
            clientsObservableList.addAll(managerPayment.getAllClients());
        } else {
            System.out.println("there is no clients");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (clientsObservableList.isEmpty()){
           allClientsList.setPlaceholder(new Label("No Client found !"));
        }else{
            allClientsList.setItems(clientsObservableList);
            allClientsList.setCellFactory(clientsListView -> new ClientsListViewCell());
        }
    }

}
