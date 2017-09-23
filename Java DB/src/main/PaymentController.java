package main;



import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDate;
import java.time.chrono.Chronology;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
public class PaymentController  {
    
	private Payment payment;
	private PaymentController paymentontroller;
    private TableView<PaymentController> tblRecord;
    private TableColumn<PaymentController, String> colPatient;;
    private TableColumn<PaymentController, String> colProcedure;
    private Button btnAddPayment;
    private Button btnDeletePayment;
    private Button btnHome;
    private TextField txtAmount;
    public TextField txtDate;
    private TextField txtNum;
    private Button btnSaveRecord;
    private ChangeListener<String> idListener;
    private ChangeListener<String> amountListener;
    private ChangeListener<String> dateListener;
  
    public void setPatientRecord(PaymentController paymentontroller) {
        if (this.paymentontroller != null) {
            unhookListener();
        }
        this.paymentontroller = paymentontroller;
        hookTo(paymentontroller);
    }
 
    private void hookTo(PaymentController payment) {
        if (payment == null) {
            clearPatientRecordFields();
        } else {
            txtNum.setText(payment.getPaymentNum());
            txtAmount.setText(payment.getPaymentAmt());
            txtDate.setText(payment.getDate());
          
          
  
            idListener = ((observable, oldValue, newValue) -> {
            	payment.setPaymentNum(newValue);
            });
            amountListener = ((observable, oldValue, newValue) -> {
            	payment.setPaymentAmt(newValue);
            });
            dateListener = ((observable, oldValue, newValue) -> {
            	payment.setPayDate(newValue);
            });
           
           
            txtNum.textProperty().addListener(idListener);
            txtAmount.textProperty().addListener(amountListener);
            txtDate.textProperty().addListener(dateListener);
            
        }
    }
    
 

	

	private void unhookListener() {
        btnSaveRecord.disableProperty().unbind();
        txtNum.textProperty().removeListener(idListener);
        txtAmount.textProperty().removeListener(amountListener);
        txtDate.textProperty().removeListener(dateListener);
       
    }
    private void clearPatientRecordFields() {
        txtNum.setText("");
        txtAmount.setText("");
      ;
        txtDate.setText("");
     
    }
    public void savePatientRecordChanges(ActionEvent actionEvent)  {
    	payment.save();
    }
    public void addPatientRecord(ActionEvent actionEvent) {
    	PaymentController paymentontroller = new PaymentController();
        tblRecord.getItems().add(0,paymentontroller);
        tblRecord.getSelectionModel().selectFirst();
        setPatientRecord(paymentontroller);
    }
    public void deletePatientRecord(ActionEvent actionEvent)  {
    	PaymentController selected = tblRecord.getSelectionModel().getSelectedItem();
        if(selected != null){
            tblRecord.getItems().remove(selected);
            selected.delete();
        }
    }
    public void goHome(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
  
    }