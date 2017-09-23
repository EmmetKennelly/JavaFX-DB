package main;
import javafx.application.Platform;
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
import java.util.Optional;
public class PatientController {
    private Patient patient;
    private TextField txtFname;
    private TextField txtLname;
    private TextField txtPhoneNum;
    private TextField txtAdd;
    private TextField txtID;
    private TableView<Patient> tblPatient;
    private TableColumn<Patient, String> colLastName;
    private TableColumn<Patient, String> colFirstName;
    private Button btnAddPatient;
    private Button btnDeletePatient;
    private Button btnPatientSave;
    private ChangeListener<String> firstNameListener;
    private ChangeListener<String> lastNameListener;
    private ChangeListener<String> phoneNumberListener;
    private ChangeListener<String> AddListener;
    private ChangeListener<String> IDListener;
    
    public void setPatient(Patient patient) {
        if (this.patient != null) {
            unhookListener();
        }
        this.patient = patient;
        hookTo(patient);
    }
 
    private void hookTo(Patient patient) {
        if (patient == null) {
            clearPatientFields();
        } else {
            txtFname.setText(patient.getPatName());
            txtLname.setText(patient.getSurname());
            txtPhoneNum.setText(patient.getPatPhoneNo());
            txtAdd.setText(patient.getPatAddress());
            txtID.setText(String.patient.getpID());

            firstNameListener = ((observable, oldValue, newValue) -> {
                patient.setPatName(newValue);
            });
            lastNameListener = ((observable, oldValue, newValue) -> {
                patient.setPatName(newValue);
            });
            phoneNumberListener = ((observable, oldValue, newValue) -> {
                patient.setPatPhoneNo(newValue);
            });
            AddListener = ((observable, oldValue, newValue) -> {
                patient.setPatAddress(newValue);
            });
           
            IDListener = ((observable, oldValue, newValue) -> {
                patient.setpID(newValue);
            });
         
            txtFname.textProperty().addListener(firstNameListener);
            txtLname.textProperty().addListener(lastNameListener);
            txtPhoneNum.textProperty().addListener(phoneNumberListener);
            txtAdd.textProperty().addListener(AddListener);
           
            txtID.textProperty().addListener(IDListener);
        }
    }
  
    private void unhookListener() {
        btnPatientSave.disableProperty().unbind();
        txtLname.textProperty().removeListener(lastNameListener);
        txtFname.textProperty().removeListener(firstNameListener);
        txtPhoneNum.textProperty().removeListener(phoneNumberListener);
        txtAdd.textProperty().removeListener(AddListener);
        txtID.textProperty().removeListener(IDListener);
    }
    private void clearPatientFields() {
        txtFname.setText("");
        txtLname.setText("");
        txtPhoneNum.setText("");
        txtAdd.setText("");
        txtID.setText("");
    }
    
  
    
   
    
    public void goHome(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}