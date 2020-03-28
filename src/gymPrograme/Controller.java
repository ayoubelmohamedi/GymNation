package gymPrograme;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private ListView<Clients> listviewPayment_ID;

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
        listviewPayment_ID.setItems(clientsObservableList);
        listviewPayment_ID.setCellFactory(clientsListView -> new ClientsListViewCell());
    }
}
