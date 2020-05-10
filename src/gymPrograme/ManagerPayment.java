package gymPrograme;

import javafx.collections.ObservableList;

import java.text.DateFormat;
import java.text.ParseException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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
            clients22.getPaymentDate().setNeedsToPay(false);
            Database.insertPaymentDate(TIME_TO_PAY, clients22);

        } else {
            System.out.println(clients22.getName() + " is already on database.");
        }
    }

    public boolean isClientExist(Clients clientForCheck) {
        return clientsList.contains(clientForCheck);
    }

    public void paySubscription(Clients clientToPay) {
        if (isClientExist(clientToPay)) {
            if (needsToPayStatus(clientToPay)) {
                Calendar updatePayment = database_instance.getPaymentDate(clientToPay);
                updatePayment.add(Calendar.MONTH, 2);
                database_instance.modifyPaymentDate(updatePayment, clientToPay);

                clientToPay.getPaymentDate().setNeedsToPay(false);
                System.out.println(clientToPay.getName() + " SUCCESSFULY PAYED");
            } else {
                System.out.println(clientToPay.getName() + " already paid !");
            }
        } else {
            System.out.println("NO such client found with the name of " + clientToPay.getName());
        }
    }

    //this method work for editing client's info
    public void editClientInfo(Clients oldClient, Clients newClient) {
        database_instance.updateClientInfo(oldClient, newClient);
    }

    //edit payment date
    public void editPaymentDate(Clients oldClient, Clients newClient, Calendar newPaymentDate) {
        //equals refer to ID between two clients
        String newDate = getDate(newPaymentDate);
        if ((!oldClient.equals(newClient)) || (!(getDate(database_instance.getPaymentDate(oldClient)).equals(newDate)))) {
            System.out.println("client new payment date is = " + newDate + "and ID is "+newClient.getIdCard());
//            database_instance.updateClientPayment(newPaymentDate, oldClient, newClient);
            database_instance.modifyPaymentDate(newPaymentDate,newClient);
            System.out.println("payment date changed");
        }
    }

    public void deleteClient(Clients clients) {
        if (clients != null) {
            database_instance.deleteClientInfo(clients);
            database_instance.deleteClientPayment(clients);
        }
    }

    public boolean needsToPayStatus(Clients clients) {
        Date payDate = database_instance.getPaymentDate(clients).getTime();
        Date currentDate = Calendar.getInstance().getTime();

        System.out.println("paymentDate = "+ payDate );
        System.out.println("current Date = " + currentDate);
        if (payDate.compareTo(currentDate) > 0) {
            clients.getPaymentDate().setNeedsToPay(false);
            return false;
        }else {
            clients.getPaymentDate().setNeedsToPay(true);
            return true;
        }
    }

    private String getDate(Calendar cal) {
        return "" + (cal.get(Calendar.MONTH) + 1) + "/" + (cal.get(Calendar.DATE)) + "/" + cal.get(Calendar.YEAR);
    }

    public String getClientPayDate(Clients clients) {
        return getDate(database_instance.getPaymentDate(clients));
    }

    public String Daysbetween(Clients client) {

        if (isClientExist(client)) {
            Calendar currentDate = Calendar.getInstance();
            Calendar paymentDate = database_instance.getPaymentDate(client);

            DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate date1 = LocalDate.of(paymentDate.get(Calendar.YEAR), paymentDate.get(Calendar.MONTH), paymentDate.get(Calendar.DATE));
            LocalDate date2 = LocalDate.of(currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE));

            long daysBetween = ChronoUnit.DAYS.between(date1, date2);
            String text = "passed day(s) : " + daysBetween;
            return text;
        }
        return "passed day(s) : none";

    }

}
