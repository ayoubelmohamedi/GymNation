package gymPrograme;

import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ManagerPayment {

    public static ObservableList<Clients> clientsList;
    Database database_instance = Database.getInstance();

    public ManagerPayment() {
        super();
        clientsList = Database.fetchDataBase();
    }

    public ObservableList<Clients> getAllClients() {
        return clientsList;
    }

    public void checker(Clients clientObject) {

        if (isClientExist(clientObject)) {
            System.out.println("found " + clientObject.getName() + " payment status : "
                    + needsToPayStatus(clientObject));
            System.out.println("-----> " + getDate(database_instance.getPaymentDate(clientObject)));
        } else {
            System.out.println("No such client with the name of : " + clientObject.getName());
        }
        System.out.println("-----------------------");
    }

    public void addClient(Clients clients22) {
        if (!isClientExist(clients22)) {
            Database.insertToDatabase(clients22);
            Calendar TIME_TO_PAY = clients22.getPaymentDate().getRegisteredDate();
            TIME_TO_PAY.add(Calendar.MONTH, 1);
            Database.insertPaymentDate(TIME_TO_PAY, clients22);

        } else {
            System.out.println(clients22.getName() + " is already on database.");
        }
    }

    public boolean isClientExist(Clients clientForCheck) {
        for (Clients client : clientsList) {
            if (client.getName().trim().equals(clientForCheck.getName()) || client.getIdCard().trim().equals(clientForCheck.getIdCard())) {
                return true;
            }
        }
        return false;
    }

    public void paySubscription(Clients clientToPay) {
        if (isClientExist(clientToPay)) {
            if (needsToPayStatus(clientToPay)) {
                Calendar clientsLastPayment = database_instance.getPaymentDate(clientToPay);
                Calendar updatePayment = clientsLastPayment;
                updatePayment.add(Calendar.MONTH, 1);
                database_instance.modifyPaymentDate(updatePayment, clientToPay);
                //TODO : store payment time in database
//				Database.editPayment(clientToPay);
                clientToPay.getPaymentDate().setNeedsToPay(false);
                System.out.println(clientToPay.getName() + " SUCCESSFULY PAYED");
            } else {
                System.out.println(clientToPay.getName() + " already paid !");
            }
        } else {
            System.out.println("NO such client found with the name of " + clientToPay.getName());
        }

    }

    public boolean needsToPayStatus(Clients clients) {
        Date payDate = database_instance.getPaymentDate(clients).getTime();
        Date currentDate = Calendar.getInstance().getTime();

        System.out.println("payment day : " + payDate);
        System.out.println("current day : " + currentDate);
        if (payDate.compareTo(currentDate) >= 0) {
            clients.getPaymentDate().setNeedsToPay(false);
            return false;
        }
        clients.getPaymentDate().setNeedsToPay(true);
        return true;
    }

    private String getDate(Calendar cal) {
        return "" + cal.get(Calendar.DATE) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.YEAR);
    }

}
