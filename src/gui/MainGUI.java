package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import repository.AppointmentRepo;
import repository.DoctorRepo;
import repository.PatientRepo;
import service.AppointmentController;
import service.DoctorController;
import service.PatientController;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MainGUI extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        PatientRepo patientRepo = new PatientRepo();
        DoctorRepo doctorRepo = new DoctorRepo();
        AppointmentRepo appointmentRepo = new AppointmentRepo();
        InputStream inputStream = new FileInputStream("src/config.properties");
        Properties properties = new Properties();
        properties.load(inputStream);
        patientRepo.read_patients_txt(properties.getProperty("patienttxtfilename"));
        doctorRepo.read_doctors_txt(properties.getProperty("doctortxtfilename"));
        appointmentRepo.read_appointments_txt(patientRepo,doctorRepo,properties.getProperty("appointmenttxtfilename"));

        GUI gui = new GUI(new DoctorController(doctorRepo), new PatientController(patientRepo), new AppointmentController(appointmentRepo));


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hello-view.fxml"));
        loader.setController(gui);
        Parent root = (Parent)loader.load();

        Scene scene = new Scene(root, 1000, 600);
        stage.setTitle("Doctors GUI");
        stage.setScene(scene);
        // define an on close function that saves all data to db, txt and bin
        stage.setOnCloseRequest(e -> {

            patientRepo.write_patients_txt(properties.getProperty("patienttxtfilename"));
            doctorRepo.write_doctors_txt(properties.getProperty("doctortxtfilename"));
            appointmentRepo.write_appointments_txt(properties.getProperty("appointmenttxtfilename"));
            patientRepo.write_patients_binary(properties.getProperty("patientbinfilename"));
            doctorRepo.write_doctors_binary(properties.getProperty("doctorbinfilename"));
            appointmentRepo.write_appointments_binary(properties.getProperty("appointmentbinfilename"));
            patientRepo.saveToDB();
            doctorRepo.saveToDB();
            appointmentRepo.saveToDB();
        });

        stage.show();
    }

    public static void main(String[] args) {
        org.burningwave.core.assembler.StaticComponentContainer.Modules.exportAllToAll();
        launch();
    }
}
