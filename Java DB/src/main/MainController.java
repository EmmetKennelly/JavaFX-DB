package main;


import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;

public class MainController {
    public ImageView imgMain;
   
    public void onClickManagePatients(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("Patient.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    public void onClickManageProcedures(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("Procedure.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    public void onClickManageInvoice(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("Invoice.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    public void onClickManagePayment(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("Payment.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

}