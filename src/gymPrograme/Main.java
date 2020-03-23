package gymPrograme;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Calendar;
import java.util.Date;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
		primaryStage.setTitle("Gym Nation");
		primaryStage.setScene(new Scene(root,600,400));
		primaryStage.show();

	}

	public static void main(String[] args) {

		launch(args);

		DateTime saidDate = new DateTime(2020, 3, 2);
		DateTime karimDate = new DateTime(2020, 2, 2);
		DateTime youssefDate = new DateTime(2020, 2, 12);

		Clients said = new Clients("Said", "AB55521","0613996979", 20, saidDate);
		Clients karim = new Clients("Karim","AK15154","0654521585",21, karimDate);
		Clients youssef = new Clients("Youssef", "AD45435", "06134528", 15, youssefDate);

		ManagerPayment managerPayment = new ManagerPayment();


//		managerPayment.addClient(said);
//		managerPayment.addClient(karim);
//		managerPayment.addClient(youssef);
		
		managerPayment.getAllClients();
//		System.out.println("---------------");
//		managerPayment.paySubscription(youssef);
//		managerPayment.checker(said);
//		managerPayment.checker(karim);
//		managerPayment.checker(youssef);
//		managerPayment.paySubscription(karim);
//		managerPayment.checker(karim);
		
	
		
		
		
	}


}
