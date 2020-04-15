package gymPrograme;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class PaymentPageController implements Initializable {

    @FXML
    public ListView<Clients> paymentClientList;

    public static ObservableList<Clients> clientsToPay;

    private Task<ObservableList<Clients>> task;

    ManagerPayment managerPayment;

    public PaymentPageController() {
        clientsToPay = FXCollections.observableArrayList();
        managerPayment = new ManagerPayment();
        startThread();
    }

//    public void initialize(){
//        task = new Task<>() {
//            @Override
//            protected ObservableList<Clients> call() {
//                for (Clients client : managerPayment.getAllClients()) {
//                    if (managerPayment.needsToPayStatus(client)) {
//                        clientsToPay.add(client);
//                    }
//                }
//                return clientsToPay;
//            }
//        };
////        payClientsListView.setItems(clientsToPay);
////        payClientsListView.setCellFactory(clientsListView -> new ClientsListViewCell());
//        payClientsListView.itemsProperty().bind(task.valueProperty());
//    }

    private void startThread() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    for (Clients client : managerPayment.getAllClients()) {
                        if (managerPayment.needsToPayStatus(client)) {
                            if (!isOnList(client)) {
                                clientsToPay.add(client);
                            }
                        }
                    }
                    try {
                        Thread.sleep(100000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.setDaemon(true);
        thread.start();

    }

    private boolean isOnList(Clients clientSelected) {
        if (!clientsToPay.isEmpty()) {
            for (Clients client : clientsToPay) {
                return clientSelected == client;
            }
        }
        return false;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (clientsToPay.isEmpty()) {

        } else {
            paymentClientList.setItems(clientsToPay);
            paymentClientList.setCellFactory(clientsListView -> new ClientsListViewCell());
        }
    }
}
