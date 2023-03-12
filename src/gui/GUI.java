package gui;
import domain.Appointment;
import domain.Doctor;
import domain.Patient;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Pair;
import service.*;
import repository.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class GUI{
    private DoctorController DC;
    private PatientController PC;
    private AppointmentController AC;

    @FXML
    private void initialize(){
        this.DoctorIDColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        this.DoctorNameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        this.DoctorSpecColumn.setCellValueFactory(new PropertyValueFactory<>("Specialization"));
        this.DoctorSalaryColumn.setCellValueFactory(new PropertyValueFactory<>("Salary"));

        this.PatientIDColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        this.PatientNameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        this.PatientAgeColumn.setCellValueFactory(new PropertyValueFactory<>("Age"));
        this.PatientPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("Phone_number"));

        this.AppointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        this.AppointmentPIDColumn.setCellValueFactory(new PropertyValueFactory<>("PID"));
        this.AppointmentDIDColumn.setCellValueFactory(new PropertyValueFactory<>("DID"));
        this.AppointmentDateColumn.setCellValueFactory(new PropertyValueFactory<>("Date"));
    }

    public GUI(DoctorController DC, PatientController PC, AppointmentController AC) {
        this.DC = DC;
        this.PC = PC;
        this.AC = AC;
    }

    @FXML
    public void clearDoctorFields(){
        this.DoctorIDFieldFX.setText("");
        this.DoctorNameFieldFX.setText("");
        this.DoctorSpecFieldFX.setText("");
        this.DoctorSalaryFieldFX.setText("");
    }

    @FXML
    public void clearPatientFields(){
        this.PatientIDFieldFX.setText("");
        this.PatientNameFieldFX.setText("");
        this.PatientAgeFieldFX.setText("");
        this.PatientPhoneFieldFX.setText("");
    }


    @FXML
    public void clearAppointmentFields(){
        this.AppointmentIDFieldFX.setText("");
        this.AppointmentPIDFieldFX.setText("");
        this.AppointmentDIDFieldFX.setText("");
        this.AppointmentDateFieldFX.setText("");
    }
    @FXML
    private TextField DoctorIDFieldFX = new TextField();

    @FXML
    private TextField DoctorNameFieldFX = new TextField();

    @FXML
    private TextField DoctorSpecFieldFX = new TextField();

    @FXML
    private TextField DoctorSalaryFieldFX = new TextField();

    @FXML
    private TextField PatientIDFieldFX = new TextField();

    @FXML
    private TextField PatientNameFieldFX = new TextField();

    @FXML
    private TextField PatientAgeFieldFX = new TextField();

    @FXML
    private TextField PatientPhoneFieldFX = new TextField();

    @FXML
    private TextField AppointmentIDFieldFX = new TextField();

    @FXML
    private TextField AppointmentDateFieldFX = new TextField();

    @FXML
    private TextField AppointmentDIDFieldFX = new TextField();

    @FXML
    private TextField AppointmentPIDFieldFX = new TextField();


    @FXML
    private TableView<Doctor> DoctorTableFX = new TableView<>();

    @FXML
    private TableColumn<Doctor, Integer> DoctorIDColumn = new TableColumn<>();

    @FXML
    private TableColumn<Doctor, String> DoctorNameColumn = new TableColumn<>();

    @FXML
    private TableColumn<Doctor, String> DoctorSpecColumn = new TableColumn<>();

    @FXML
    private TableColumn<Doctor, Integer> DoctorSalaryColumn = new TableColumn<>();

    @FXML
    private TableView<Patient> PatientTableFX = new TableView<>();

    @FXML
    private TableColumn<Patient, Integer> PatientIDColumn = new TableColumn<>();

    @FXML
    private TableColumn<Patient, String> PatientNameColumn = new TableColumn<>();

    @FXML
    private TableColumn<Patient, Integer> PatientAgeColumn = new TableColumn<>();

    @FXML
    private TableColumn<Patient, String> PatientPhoneColumn = new TableColumn<>();

    @FXML
    private TableView<Appointment> AppointmentTableFX = new TableView<>();

    @FXML
    private TableColumn<Appointment, Integer> AppointmentIDColumn = new TableColumn<>();

    @FXML
    private TableColumn<Appointment, String> AppointmentDateColumn = new TableColumn<>();

    @FXML
    private TableColumn<Appointment, Integer> AppointmentDIDColumn = new TableColumn<>();

    @FXML
    private TableColumn<Appointment, Integer> AppointmentPIDColumn = new TableColumn<>();

    @FXML
    private void setDoctorTable(){
        this.DoctorTableFX.getItems().clear();
        for(Doctor d : this.DC.getDoctorRepo().getDoctors()){
            this.DoctorTableFX.getItems().add(d);
        }
    }


    @FXML
    private void onDoctorAddClicked(){
        try {
            int id = Integer.parseInt(DoctorIDFieldFX.getText());
            String name = DoctorNameFieldFX.getText();
            String spec = DoctorSpecFieldFX.getText();
            int salary = Integer.parseInt(DoctorSalaryFieldFX.getText());
            DC.addDoctor(new Doctor(id, name, spec, salary));
            this.setDoctorTable();
            this.clearDoctorFields();

        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void onDoctorDeleteClicked(){
        try {
            int id = Integer.parseInt(DoctorIDFieldFX.getText());
            DC.removeDoctor(id);
            this.setDoctorTable();
            this.clearDoctorFields();
        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void onDoctorUpdateClicked(){
        try {
            int id = Integer.parseInt(DoctorIDFieldFX.getText());
            String name = DoctorNameFieldFX.getText();
            String spec = DoctorSpecFieldFX.getText();
            int salary = Integer.parseInt(DoctorSalaryFieldFX.getText());
            DC.updateDoctor(id,new Doctor(id, name, spec, salary));
            this.setDoctorTable();
            this.clearDoctorFields();
        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void onShowAllDoctorsClicked(){
        this.setDoctorTable();
        this.setPatientTable();
        this.setAppointmentTable();
    }

    private void setAppointmentTable() {
        this.AppointmentTableFX.getItems().clear();
        for(Appointment a : this.AC.getAppointmentRepo().getAppointments()){
            this.AppointmentTableFX.getItems().add(a);
        }
    }

    private void setPatientTable() {
        this.PatientTableFX.getItems().clear();
        for(Patient p : this.PC.getPatientRepo().getPatients()){
            this.PatientTableFX.getItems().add(p);
        }
    }

    @FXML
    private void onAddPatientClicked(){
        try {
            int id = Integer.parseInt(PatientIDFieldFX.getText());
            String name = PatientNameFieldFX.getText();
            int age = Integer.parseInt(PatientAgeFieldFX.getText());
            String phone = PatientPhoneFieldFX.getText();
            PC.addPatient(new Patient(id, name,  phone,age));
            this.setPatientTable();
            this.clearPatientFields();
        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void onDeletePatientClicked(){
        try {
            int id = Integer.parseInt(PatientIDFieldFX.getText());
            PC.removePatient(id);
            this.setPatientTable();
            this.clearPatientFields();
        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void onShowAllPatientsClicked(){
        this.setPatientTable();
        this.setDoctorTable();
        this.setAppointmentTable();
    }

    @FXML
    private void onUpdatePatientClicked(){
        try {
            int id = Integer.parseInt(PatientIDFieldFX.getText());
            String name = PatientNameFieldFX.getText();
            int age = Integer.parseInt(PatientAgeFieldFX.getText());
            String phone = PatientPhoneFieldFX.getText();
            PC.updatePatient(id,new Patient(id, name,  phone,age));
            this.setPatientTable();
            this.clearPatientFields();
        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void onAddAppointmentClicked(){
        try {
            int id = Integer.parseInt(AppointmentIDFieldFX.getText());
            String date = AppointmentDateFieldFX.getText();
            int did = Integer.parseInt(AppointmentDIDFieldFX.getText());
            int pid = Integer.parseInt(AppointmentPIDFieldFX.getText());
            AC.addAppointment(new Appointment(id, DC.getDoctor(did), PC.getPatient(pid), LocalDate.parse(date)));
            this.setAppointmentTable();
            this.clearAppointmentFields();
        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void onDeleteAppointmentClicked(){
        try {
            int id = Integer.parseInt(AppointmentIDFieldFX.getText());
            AC.removeAppointment(id);
            this.setAppointmentTable();
            this.clearAppointmentFields();
        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void onUpdateAppointmentClicked(){
        try {
            int id = Integer.parseInt(AppointmentIDFieldFX.getText());
            String date = AppointmentDateFieldFX.getText();
            int did = Integer.parseInt(AppointmentDIDFieldFX.getText());
            int pid = Integer.parseInt(AppointmentPIDFieldFX.getText());
            AC.updateAppointment(id,new Appointment(id, DC.getDoctor(did), PC.getPatient(pid), LocalDate.parse(date)));
            this.setAppointmentTable();
            this.clearAppointmentFields();
        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    @FXML
    private void onGetAllInADayClicked(){

        try {

            String date = AppointmentDateFieldFX.getText();
            this.AppointmentTableFX.getItems().clear();
            ArrayList<Appointment> appointments = AC.getAllInAday(LocalDate.parse(this.AppointmentDateFieldFX.getText()));
            for(Appointment a : appointments){
                this.AppointmentTableFX.getItems().add(a);
            }
            clearAppointmentFields();
        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }

    @FXML
    private void onShowAllAppointmentsClicked(){
        this.setAppointmentTable();
        this.setDoctorTable();
        this.setPatientTable();
    }

}