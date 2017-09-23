package main;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;

public class InvoiceContorller {
    private Invoice invoice;
    private TableView tblRecordInfo;

    private TableColumn<Invoice, String> colInvoice;

    private TableColumn<Invoice, String> colDate;

    public void InvoiceContorller(Invoice invoice) {
        if (this.invoice != null) {
            unhookListener();
        }
        this.invoice = invoice;
        hookTo(invoice);
    }
    private void hookTo(Invoice invoice) {
        if (invoice == null) {
        
        } else {
        	colInvoice.setText(String.invoice.getInvoiceNo());
            colDate.setText(invoice.getInvoiceDate());
            
    }
    }
    private Button btnHome;
   
    public void goHome(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        Scene scene = new Scene(root);
        
        Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}