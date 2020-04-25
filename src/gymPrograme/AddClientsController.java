package gymPrograme;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddClientsController {
    

    @FXML
    private TextField namefield_id, identificationField_id, phoneNumber_id, ageField_id;

    @FXML
    private DatePicker calenderField_id;

    ManagerPayment managerPayment;
    PaymentPageController paymentPageController;

    public AddClientsController() {
        managerPayment = new ManagerPayment();
        paymentPageController = new PaymentPageController();
    }

    public void saveButtonAction() {

        String clientName = namefield_id.getText();
        String clientID = identificationField_id.getText();
        String clientPhone = phoneNumber_id.getText();
        int clientAge;
        String clientDate = calenderField_id.getEditor().getText();

        if (managerPayment != null) {
            if ((!isFieldEmpty(namefield_id, identificationField_id, calenderField_id)) && validateJavaDate(clientDate)) {
                if (isIntTextField(ageField_id)) {
                    clientAge = Integer.parseInt(ageField_id.getText());
                } else {
                    clientAge = 0;
                    System.out.println("(is empty or null) give default value 0");
                }

                String[] dateParts = clientDate.split("/");
                int month = Integer.parseInt(dateParts[0]);
                int day = Integer.parseInt(dateParts[1]);
                int year = Integer.parseInt(dateParts[2]);

                DateTime newDateTime = new DateTime(year, month, day);
                Clients newClients = new Clients(clientName, clientID, clientPhone, clientAge, newDateTime);
                managerPayment.addClient(newClients);
                if (!managerPayment.isClientExist(newClients)) {
                    if (managerPayment.needsToPayStatus(newClients)) {
                        paymentPageController.addToList(newClients);
                    }
                    Controller.clientsObservableList.add(newClients);
                    System.out.println(clientName + " added successfully :D ");
                }

                clearFields();
            }
        } else {
            System.out.println("managerPayment = null !!");

        }
    }

    public void cancelSaving(){
        clearFields();
    }

    private boolean validateJavaDate(String strDate) {
        if (!strDate.trim().equals("")) {
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
        } else {
            return true;
        }
    }

    //clear fields of adding clients after pressing "save"
    private void clearFields() {
        namefield_id.setText("");
        identificationField_id.setText("");
        phoneNumber_id.setText("");
        ageField_id.setText("");
        calenderField_id.getEditor().setText("");
    }

    //check if textFiled contains int value
    private boolean isIntTextField(TextField textField) {
        if (!(textField.getText().isEmpty() || textField.getText() == null)) {
            if ((Integer.parseInt(textField.getText())) % 1 == 0) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    private boolean isFieldEmpty(TextField namefield, TextField clientId, DatePicker calenderField) {
        TextField[] fields = {namefield, clientId, calenderField.getEditor()};
        int value = 0;
        for (TextField field : fields) {
            if (field.getText().trim().isEmpty() || field.getText() == null) {
                value = 0;
            } else {
                value = 1;
            }
        }
        if (value == 1) {
            return false;
        }
        return true;
    }

}
