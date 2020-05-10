package gymPrograme;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class PaymentPageController implements Initializable {

    @FXML
    private ListView<Clients> paymentClientList;

    @FXML
    private TextField searchBar_id;

    public static ObservableList<Clients> clientsToPay;

//    private Task<ObservableList<Clients>> task;

    ManagerPayment managerPayment;

    public PaymentPageController() {
        clientsToPay = FXCollections.observableArrayList();
        managerPayment = new ManagerPayment();
        startThread();
    }

    private void startThread() {
        Thread thread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                for (Clients client : managerPayment.getAllClients()) {
                    System.out.println(client.getName()+" status : " + managerPayment.needsToPayStatus(client));
                    if (managerPayment.needsToPayStatus(client)) {
                        addToList(client);
                    }
                }
                try {
                    Thread.sleep(100000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }


    public void addToList(Clients clients) {
        if (!clientsToPay.contains(clients)) {
            clientsToPay.add(clients);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (clientsToPay.isEmpty()) {
            paymentClientList.setPlaceholder(new Label("No Client found !"));
        } else {
            paymentClientList.setItems(clientsToPay);
            paymentClientList.setCellFactory(clientsListView -> new ClientToPayListCell());

            // 1. Wrap the ObservableList in a FilteredList (initially display all data).
            FilteredList<Clients> filteredData = new FilteredList<>(clientsToPay, p -> true);

            // 2. Set the filter Predicate whenever the filter changes.
            searchBar_id.textProperty().addListener((observable, oldValue, newValue) -> {
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
            paymentClientList.setItems(sortedData);
        }
    }
}
