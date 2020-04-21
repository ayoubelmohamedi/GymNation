package gymPrograme;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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

    @FXML
    private TextField searchBarMain_id;

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
        if (clientsObservableList.isEmpty()) {
            allClientsList.setPlaceholder(new Label("No Client found !"));
        } else {
            allClientsList.setItems(clientsObservableList);
            allClientsList.setCellFactory(clientsListView -> new ClientsListViewCell());

            // 1. Wrap the ObservableList in a FilteredList (initially display all data).
            FilteredList<Clients> filteredData = new FilteredList<>(clientsObservableList, p -> true);

            // 2. Set the filter Predicate whenever the filter changes.
            searchBarMain_id.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(clients -> {
                    // If filter text is empty, display all persons.
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    // Compare first name and last name of every person with filter text.
                    String lowerCaseFilter = newValue.toLowerCase();

                    if (clients.getName().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches first name.
                    } else if (clients.getIdCard().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches Id.
                    }
                    return false; // Does not match.
                });
            });

            // 3. Wrap the FilteredList in a SortedList.
            SortedList<Clients> sortedData = new SortedList<>(filteredData);

            // 4. Bind the SortedList comparator to the TableView comparator.
//            sortedData.comparatorProperty().bind(paymentClientList.comparatorProperty());

            // 5. Add sorted (and filtered) data to the table.
            allClientsList.setItems(sortedData);
        }
    }

}
