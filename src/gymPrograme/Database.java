package gymPrograme;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Database {

    private static Database single_instance = null;

    // private constructor restricted to this class itself
    private Database() {

    }

    public static Database getInstance() {
        if (single_instance == null)
            single_instance = new Database();

        return single_instance;
    }

    private static Connection connection = null;
    private static Statement statement = null;
    private static PreparedStatement preparedStatement = null;

    public static void connectToDataBase() {

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:gymDataBase.db");
            statement = connection.createStatement();
            statement.setQueryTimeout(30);

            ResultSet result = statement.executeQuery("SELECT * FROM CLIENTS");
            while (result.next()) {
                System.out.println("Client's name : " + result.getString("name"));
                System.out.println("Client's Id : " + result.getString("ID"));
                System.out.println("--------");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }

        }
    }

    public static void insertToDatabase(Clients clients) {
        if (clients != null) {
            try {
                String sqlite = "insert into CLIENTS( name, phone, ID, age, day,month,year) values (?,?,?,?,?,?,?)";
                String number = clients.getPhoneNumber();
                long longNumber = Long.parseLong(number);
                connection = DriverManager.getConnection("jdbc:sqlite:gymDataBase.db");
                statement = connection.createStatement();

                preparedStatement = connection
                        .prepareStatement(sqlite);
                preparedStatement.setString(1, clients.getName());
                preparedStatement.setLong(2, longNumber);
                preparedStatement.setString(3, clients.getIdCard());
                preparedStatement.setInt(4, clients.getAge());
                preparedStatement.setInt(5, clients.getPaymentDate().getRegisteredDate().get(Calendar.DATE));
                preparedStatement.setInt(6, clients.getPaymentDate().getRegisteredDate().get(Calendar.MONTH));
                preparedStatement.setInt(7, clients.getPaymentDate().getRegisteredDate().get(Calendar.YEAR));
                preparedStatement.executeUpdate();

                System.out.println(clients.getName() + " Added successfully to the database :D");

//				statement.execute("insert into CLIENTS (name,id,phone,age) values (" + clients.getName() + ", "
//						+ clients.getIdCard() + ", " + longNumber + ", " + clients.getAge() + ")");
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("Error , you must field the client info");
        }
    }

    public static ObservableList<Clients> fetchDataBase() {
        ObservableList<Clients> arrayList = FXCollections.observableArrayList();
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:gymDataBase.db");
            statement = connection.createStatement();
            String s = "SELECT * FROM CLIENTS";
            ResultSet res = statement.executeQuery(s);
            while (res.next()) {
                Clients clients = new Clients(res.getString("name"), res.getString("ID"), res.getString("phone"),
                        res.getInt("age"), new DateTime(res.getInt("year"), res.getInt("month"), res.getInt("day")));
                arrayList.add(clients);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return arrayList;
    }

    public static void insertPaymentDate(Calendar time_to_pay, Clients clients22) {
        try {
            int DAY = time_to_pay.get(Calendar.DATE);
            int MONTH = time_to_pay.get(Calendar.MONTH);
            int YEAR = time_to_pay.get(Calendar.YEAR);

            String clientsName = clients22.getIdCard();

            String sqlite = "insert into PAYMENTS( clientID, payDay, payMONTH, payYEAR) values (?,?,?,?)";
            connection = DriverManager.getConnection("jdbc:sqlite:gymDataBase.db");
            statement = connection.createStatement();

            preparedStatement = connection
                    .prepareStatement(sqlite);
            preparedStatement.setString(1, clientsName);
            preparedStatement.setInt(2, DAY);
            preparedStatement.setInt(3, MONTH);
            preparedStatement.setInt(4, YEAR);

            preparedStatement.executeUpdate();

            System.out.println(clients22.getName() + " Added successfully to payment database :DD");

//		statement.execute("insert into CLIENTS (name,id,phone,age) values (" + clients.getName() + ", "
//				+ clients.getIdCard() + ", " + longNumber + ", " + clients.getAge() + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void modifyPaymentDate(Calendar newPaymentDay, Clients clients) {
        try {
            int DAY = newPaymentDay.get(Calendar.DATE);
            int MONTH = newPaymentDay.get(Calendar.MONTH);
            int YEAR = newPaymentDay.get(Calendar.YEAR);

            String clientsName = clients.getIdCard();

            String sqlite = "UPDATE PAYMENTS SET payDAY = ? , payMONTH = ? , payYEAR = ? WHERE clientID = ? ";
            connection = DriverManager.getConnection("jdbc:sqlite:gymDataBase.db");
            statement = connection.createStatement();
            preparedStatement = connection
                    .prepareStatement(sqlite);
            preparedStatement.setInt(1, DAY);
            preparedStatement.setInt(2, MONTH);
            preparedStatement.setInt(3, YEAR);
            preparedStatement.setString(4, clientsName);
            preparedStatement.executeUpdate();

            System.out.println(clients.getName() + "'s payment updated successfully :D");

//			statement.execute("insert into CLIENTS (name,id,phone,age) values (" + clients.getName() + ", "
//					+ clients.getIdCard() + ", " + longNumber + ", " + clients.getAge() + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateClientInfo(Clients oldClient, Clients newClient){
        String sqlite = "UPDATE CLIENTS SET name = ? , ID = ? ,phone = ? , age = ?  WHERE ID = ?";
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:gymDataBase.db");
            statement = connection.createStatement();

            String number = newClient.getPhoneNumber();
            long longNumber = Long.parseLong(number);

            preparedStatement = connection
                    .prepareStatement(sqlite);
            preparedStatement.setString(1, newClient.getName());
            preparedStatement.setLong(2, longNumber);
            preparedStatement.setString(3, newClient.getIdCard());
            preparedStatement.setInt(4, newClient.getAge());
            preparedStatement.setString(5,oldClient.getIdCard());

            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.fillInStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void updateClientPayment(Calendar newPaymentDate,Clients oldClient, Clients newClient){
        String sqlite = "UPDATE PAYMENTS SET ID = ? , payDAY = ? , payMONTH = ? , payYEAR = ? WHERE ID = ?";
        try{

            int DAY = newPaymentDate.get(Calendar.DATE);
            int MONTH = newPaymentDate.get(Calendar.MONTH);
            int YEAR = newPaymentDate.get(Calendar.YEAR);

            connection = DriverManager.getConnection("jdbc:sqlite:gymDataBase.db");
            statement = connection.createStatement();

            preparedStatement = connection
                    .prepareStatement(sqlite);
            preparedStatement.setString(1, newClient.getIdCard());
            preparedStatement.setInt(2, DAY);
            preparedStatement.setInt(3, MONTH);
            preparedStatement.setInt(4, YEAR);
            preparedStatement.setString(5,oldClient.getIdCard());

            preparedStatement.executeUpdate();
        }catch (Exception e){
            e.fillInStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteClientInfo(Clients clients) {
        String sqlite = "DELETE FROM CLIENTS WHERE ID = ? ";

        try{
            connection = DriverManager.getConnection("jdbc:sqlite:gymDataBase.db");
            statement = connection.createStatement();
            preparedStatement = connection.prepareStatement(sqlite);
            preparedStatement.setString(1,clients.getIdCard());
            preparedStatement.executeUpdate();

        } catch (Exception e){
        e.fillInStackTrace();
    }finally {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    }

    public void deleteClientPayment(Clients clients){
        String sqlite = "DELETE FROM PAYMENTS WHERE ID = ?";

        try{
            connection = DriverManager.getConnection("jdbc:sqlite:gymDataBase.db");
            statement = connection.createStatement();
            preparedStatement = connection.prepareStatement(sqlite);
            preparedStatement.setString(1,clients.getIdCard());
            preparedStatement.executeUpdate();

        } catch (Exception e){
            e.fillInStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteClientPayment(){

    }

    public Calendar getPaymentDate(Clients clients) {
        Calendar paymentDate = Calendar.getInstance();
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:gymDataBase.db");
            statement = connection.createStatement();
            String s = "SELECT * FROM PAYMENTS";
            ResultSet res = statement.executeQuery(s);
            while (res.next()) {
                if (res.getString("clientID").equals(clients.getIdCard())) {
                    paymentDate.set(Calendar.DATE, res.getInt("payDAY"));
                    paymentDate.set(Calendar.MONTH, res.getInt("payMONTH") - 1);
                    paymentDate.set(Calendar.YEAR, res.getInt("payYEAR"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return paymentDate;
    }

}

	
	

