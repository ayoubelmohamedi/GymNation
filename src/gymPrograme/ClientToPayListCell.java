package gymPrograme;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

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

    @FXML
    private Button payButton_id;


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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/gymPrograme/clientsInforPage.fxml"));
                try {
                    Parent root = (Parent) loader.load();
                    ClientsInfoController clientsInfoController = loader.getController();
                    clientsInfoController.intializeClient(clients);
                    Stage stage = (Stage) this.detailButton_id.getScene().getWindow();
                    stage.setScene(new Scene(root, 600, 400));
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            payButton_id.setOnAction(actionEvent -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setContentText("confirme payment ?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    managerPayment.paySubscription(clients);
                }else{
                    alert.close();
                }
            });

            setText(null);
            setGraphic(gridPaneToPay_id);

        }
    }
}
