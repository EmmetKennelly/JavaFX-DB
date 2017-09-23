package main;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main implements Serializable {

	static ArrayList<Patient> pList;

	static MainController gui;
	static ObjectInputStream input;
	FileInputStream fis;
	static ObjectOutputStream oos;
	static File procedureFile = new File("Procedures.ser");
	static DateFormat format;
	
	public void start(Stage primaryStage) throws Exception{
	        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
	        primaryStage.setTitle("JavaFx Starter Application");
	        primaryStage.setScene(new Scene(root));
	        primaryStage.show();
	    }
	

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		
		pList = new ArrayList<Patient>();

		 launch(args);

		try {
			input = new ObjectInputStream(new FileInputStream("information.txt"));

			while (true) {
				pList.add((Patient) input.readObject());

			}

		} catch (ClassNotFoundException f) {
			f.getMessage();
		} catch (IOException i) {
			i.getCause();
		}

		

		gui = new MainController();

	}

}