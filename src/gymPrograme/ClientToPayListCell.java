package gymPrograme;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class ClientToPayListCell extends ListCell<Clients> {

    ManagerPayment managerPayment = new ManagerPayment();
    @FXML
    private Label nameField_id;

    @FXML
    private Label idCardField_id;

    @FXML
    private Label paymentTime_id;

    @FXML
    private Label lateForDay_id;

    @FXML
    private GridPane gridPaneToPay_id;

    @FXML
    private Button detailButton_id;


    private FXMLLoader mLLoader;

    @Override
    protected void updateItem(Clients clients, boolean empty) {
        super.updateItem(clients, empty);


        if (empty || clients == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/gymPrograme/ListCellToPay.fxml"));
                mLLoader.setController(this);
                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            String paymentDate = managerPayment.getClientPayDate(clients);

            nameField_id.setText(clients.getName());
            idCardField_id.setText(clients.getIdCard());
            paymentTime_id.setText(paymentDate);

            lateForDay_id.setText(managerPayment.Daysbetween(clients));

            detailButton_id.setOnAction(actionEvent -> {

            });

            setText(null);
            setGraphic(gridPaneToPay_id);

        }
    }
}
