package gymPrograme;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private ListView<Clients> allClientsList;

    @FXML
    private TextField namefield_id, identificationField_id, phoneNumber_id, ageField_id;

    @FXML
    private DatePicker calenderField_id;

    ManagerPayment managerPayment;
    private ObservableList<Clients> clientsObservableList;

    public Controller() {
        clientsObservableList = FXCollections.observableArrayList();
        managerPayment = new ManagerPayment();

        //add to observableList
        clientsObservableList.addAll(managerPayment.getAllClients());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allClientsList.setItems(clientsObservableList);
        allClientsList.setCellFactory(clientsListView -> new ClientsListViewCell());
    }

    public void saveButtonAction() {
        String pattern = "MM-dd-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        String clientName = namefield_id.getText();
        String clientID = identificationField_id.getText();
        String clientPhone = phoneNumber_id.getText();
        int clientAge = Integer.valueOf(ageField_id.getText());
        String clientDate = calenderField_id.getEditor().getText();


        if (managerPayment != null) {
            if ((!clientName.isEmpty()) || (!clientID.isEmpty()) || validateJavaDate(clientDate)) {
                String[] dateParts = clientDate.split("/");
                int day = Integer.valueOf(dateParts[0]);
                int month = Integer.valueOf(dateParts[1]);
                int year = Integer.valueOf(dateParts[2]);

                DateTime newDateTime = new DateTime(year, month, day);
                Clients newClients = new Clients(clientName, clientID, clientPhone, clientAge, newDateTime);
                managerPayment.addClient(newClients);
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
}
