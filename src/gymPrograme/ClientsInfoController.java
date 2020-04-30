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
import java.util.Calendar;
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
        if (checkIFchanged(client)){
            Clients newClient = new Clients(fullName_id.getText(),idField_id.getText(),phoneNumber_id.getText(),Integer.parseInt(ageField_id.getText()),client.getPaymentDate());
            //save client info only without changing payment
            managerPayment.editClientInfo(client,newClient);
            if ((!idField_id.getText().equals(client.getIdCard()) || (!paymentDate_id.getEditor().getText().equals(managerPayment.getClientPayDate(client)))) ){
                String newPaymentdate = paymentDate_id.getEditor().getText();
                Calendar newCalendar = Calendar.getInstance();

                String [] date = newPaymentdate.split("/");
                newCalendar.set(Calendar.DATE,Integer.parseInt(date[0]));
                newCalendar.set(Calendar.MONTH,Integer.parseInt(date[1]));
                newCalendar.set(Calendar.YEAR,Integer.parseInt(date[2]));
                System.out.println(" save payment with day = "+date[0]+" and month = "+date[1] + " and year = "+ date[2]);
                //save payment date
                managerPayment.editPaymentDate(client,newClient,newCalendar);
            }
        }else {
            cancelFunc();
        }

    }

    private boolean checkIFchanged(Clients clients) {
        if (fullName_id.getText().equals(clients.getName()) && idField_id.getText().equals(clients.getIdCard())
                && ageField_id.getText().equals(String.valueOf(clients.getAge())) && phoneNumber_id.getText().equals(clients.getPhoneNumber())){
            return false;
        }
        return true;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
