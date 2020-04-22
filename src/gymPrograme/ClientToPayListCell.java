package gymPrograme;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

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

    private FXMLLoader mLLoader;

    @Override
    protected void updateItem(Clients clients, boolean empty) {
        super.updateItem(clients, empty);


        if (empty || clients == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/gymPrograme/ListCell.fxml"));
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
            //TODO compare currentDate with paymentDate in database ; return days between

            lateForDay_id.setText("");

            setText(null);
            setGraphic(gridPaneToPay_id);

        }
    }
}
