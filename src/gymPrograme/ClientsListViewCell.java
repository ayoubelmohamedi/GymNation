package gymPrograme;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientsListViewCell extends ListCell<Clients> {
    @FXML
    private Label name_id;

    @FXML
    private Label idCard_id;

    @FXML
    private GridPane gridPane_id;

    @FXML
    private RadioButton payStatus_id;

    @FXML
    private Button detailButton_id;

    private FXMLLoader mLLoader;
    private ClientsInfoController clientsInfoController;

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
            name_id.setText(clients.getName());
            idCard_id.setText(clients.getIdCard());
            detailButton_id.setOnAction(actionEvent -> {
                try {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/gymPrograme/clientsInforPage.fxml"));
                    Parent root = (Parent)loader.load();
                    clientsInfoController = loader.getController();
                    clientsInfoController.intializeClient(clients);
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root,600,400));
                    stage.show();

                }catch (Exception e){
                    e.fillInStackTrace();
                }
            });

            setText(null);
            setGraphic(gridPane_id);

        }
    }
}
