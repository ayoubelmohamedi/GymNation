package gymPrograme;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientsInfoController implements Initializable {

    @FXML
    TextField fullName_id;
    @FXML
    TextField idField_id;
    @FXML
    TextField ageField_id;
    @FXML
    TextField phoneNumber_id;
    @FXML
    DatePicker registerationDate_id;
    @FXML
    DatePicker paymentDate_id;
    @FXML
    Button cancelBtn_id;

    Clients client;
    ManagerPayment managerPayment = new ManagerPayment();

    public void intializeClient(Clients clients) {
        this.client = clients;
        if (client != null) {
            fullName_id.setText(client.getName());
            idField_id.setText(client.getIdCard());
            ageField_id.setText(String.valueOf(client.getAge()));
            phoneNumber_id.setText(client.getPhoneNumber());
            registerationDate_id.getEditor().setText(client.getPaymentDate().getRegiterationDate());
            paymentDate_id.getEditor().setText(client.getPaymentDate().getPaymentDate());
        }
    }

    public void deleteClient() {

    }

    public void cancelFunc() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gymPrograme/sample.fxml"));
            Parent root = (Parent) loader.load();
            Stage stage = (Stage) this.cancelBtn_id.getScene().getWindow();
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    public void saveFunc() {

    }

    private boolean checkIFchanged(Clients clients) {
        if ((fullName_id.getText().equals(clients.getName())) && (idField_id.getText().equals(clients.getIdCard())) ||
                (ageField_id.getText().equals(String.valueOf(clients.getAge()))) || (phoneNumber_id.getText().equals(clients.getPhoneNumber()))
                || registerationDate_id.getEditor().getText().equals(clients.getPaymentDate().getRegiterationDate())
                || paymentDate_id.getEditor().getText().equals(managerPayment.getClientPayDate(clients))) {
            return false;
        }
        return true;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
