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
public class ProcedureController {
    private Procedure procedure;
    private TableView<Procedure> tblProcedure;
    private TableColumn<Procedure, String> colName;
    private TableColumn<Procedure, String> colNo;
    private TableColumn<Procedure, String> colCost;
    private TextField txtName;
    private TextField txtNo;
    private TextField txtCost;
    private Button btnAddProcedure;
    private Button btnDeleteProcedure;
    private Button btnSaveProcedure;
    private Button btnHome;
    private ChangeListener<String> nameListener;
    private ChangeListener<String> codeListener;
    private ChangeListener<String> costListener;
   
    public void setProcedure(Procedure procedure) {
        if (this.procedure != null) {
            unhookListener();
        }
        this.procedure = procedure;
        hookTo(procedure);
    }
   
    private void hookTo(Procedure procedure) {
        if (procedure == null) {
            clearProcedureFields();
        } else {
            txtName.setText(procedure.getProcName());
            txtNo.setText(String.procedure.getProcNo());
            txtNo.setText(String.procedure.getProcCost());
            
            nameListener = ((observable, oldValue, newValue) -> {
                procedure.setProcName(newValue);
            });
            codeListener = ((observable, oldValue, newValue) -> {
                procedure.setProcNo(newValue);
            });
           costListener = ((observable, oldValue, newValue) -> {
                procedure.setProcCost(newValue);
            });
            txtName.textProperty().addListener(nameListener);
            txtNo.textProperty().addListener(codeListener);
            txtCost.textProperty().addListener(codeListener);
        }
    }
 
    private void unhookListener() {
        btnSaveProcedure.disableProperty().unbind();
        txtName.textProperty().removeListener(nameListener);
        txtNo.textProperty().removeListener(codeListener);
    }
    private void clearProcedureFields() {
        txtName.setText("");
        txtNo.setText("");
    }
    public void btnSaveClick(ActionEvent actionEvent)  {
        procedure.save();
        System.out.println("Clicked Save");
    }
    public void btnClickAddProcedure(ActionEvent actionEvent) {
        Procedure procedure = new Procedure();
        tblProcedure.getItems().add(0, procedure);
        tblProcedure.getSelectionModel().selectFirst();
        setProcedure(procedure);
        System.out.println(procedure.getProcNo());
    }
    public void btnClickDelProcedure(ActionEvent actionEvent)  {
        Procedure selected = tblProcedure.getSelectionModel().getSelectedItem();
        if (selected != null) {
            tblProcedure.getItems().remove(selected);
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