package gymPrograme;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;



public class AddClientsController {

    @FXML
    private TextField namefield_id, identificationField_id, phoneNumber_id, ageField_id;

    @FXML
    private DatePicker calenderField_id;

    ManagerPayment managerPayment;

    public AddClientsController(){
        managerPayment = new ManagerPayment();
    }

    public void saveButtonAction() {

        String clientName = namefield_id.getText();
        String clientID = identificationField_id.getText();
        String clientPhone = phoneNumber_id.getText();
        int clientAge = Integer.valueOf(ageField_id.getText());
        String clientDate = calenderField_id.getEditor().getText();

        if (managerPayment != null) {
            if ((!clientName.isEmpty()) || (!clientID.isEmpty()) || validateJavaDate(clientDate)) {
                String[] dateParts = clientDate.split("/");
                int month = Integer.valueOf(dateParts[0]);
                int day = Integer.valueOf(dateParts[1]);
                int year = Integer.valueOf(dateParts[2]);

                DateTime newDateTime = new DateTime(year, month, day);
                Clients newClients = new Clients(clientName, clientID, clientPhone, clientAge, newDateTime);
                managerPayment.addClient(newClients);
                Controller.clientsObservableList.add(newClients);

//                clientsObservableList.setAll(managerPayment.getAllClients());
//                allClientsList.getItems().setAll(clientsObservableList);
                System.out.println(clientName + " added successfully :D ");
            }
        } else {
            System.out.println("managerPayment = null !!");
        }
    }

    private boolean validateJavaDate(String strDate) {
        if (strDate.trim().equals("")) {
            return true;
        } else {
            SimpleDateFormat sdfrmt = new SimpleDateFormat("MM/dd/yyyy");
            sdfrmt.setLenient(false);
            try {
                Date javaDate = sdfrmt.parse(strDate);
                System.out.println(strDate + " is valid date format");
            } catch (ParseException e) {
                System.out.println(strDate + " is Invalid Date format");
                return false;
            }
            return true;
        }
    }
    //clear fields of adding clients after pressing "save"
    private void clearFields(){

    }

}
