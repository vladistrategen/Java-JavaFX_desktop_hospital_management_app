package repository;
import handlers.PatientBinaryFileHandler;
import handlers.PatientTxtFileHandler;
import domain.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class PatientRepo implements GenericRepo {
    private ArrayList<Patient> patients;
    private PatientTxtFileHandler txtFileHandler = new PatientTxtFileHandler();
    public PatientRepo() {
        this.patients = new ArrayList<Patient>();
    }
    public void add(Patient patient) {
        patients.add(patient);
    }
    public void remove(Patient patient) {
        patients.remove(patient);
    }
    public void remove(int ID) {
        for (Patient patient : patients) {
            if (patient.getID() == ID) {
                patients.remove(patient);
                return;
            }
        }
    }


    public void read_patients_txt(String filename){
        if (filename == null || filename.isBlank() || filename.isEmpty()) {
            throw new IllegalArgumentException("Invalid data");
        }
        patients = txtFileHandler.createPatients(filename);
    }
    
    public void write_patients_txt(String filename){
        if (filename == null ) {
            throw new IllegalArgumentException("Invalid data");
        }
        txtFileHandler.writePatients(patients, filename);
    }

    public Patient getPatient(int ID) {
        Patient result = null;
        for (Patient patient : patients) {
            if (patient.getID() == ID) {
                return patient;
            }
        }
        return result;
    }

    public void update(int ID, Patient patient) {

    }
    public void print() {
        for (Patient patient : patients) {
            System.out.println(patient);
        }
    }
    public boolean exists(Patient patient) {
        for(Patient pat : patients) {
            if(pat.getID() == patient.getID()) {
                return true;
            }
        }
        return false;
    }
    public void sort() {
        patients.sort((o1, o2) -> o1.getName().compareTo(o2.getName()));
    }

    @Override
    public void add(Object o) {
        add((Patient) o);
    }

    @Override
    public void remove(Object o) {
        remove((Patient) o);
    }

    @Override
    public void update(int ID, Object newobj) {
        patients.get(ID).update((Patient) newobj);
    }

    public ArrayList<Patient> getPatients() {
        return patients;
    }
    @Override
    public String toString() {
        String result= "";
        result+= "Patients: ";
        for(Patient patient : patients) {
            result+= patient.toString() + " ";
        }
        return result;
    }

    public void read_patients_binary(String filename){
        if (filename == null || filename.isBlank() || filename.isEmpty()) {
            throw new IllegalArgumentException("Invalid data");
        }
        patients = PatientBinaryFileHandler.deserializePatients(filename);
    }

    public void write_patients_binary(String filename){
        if (filename == null ) {
            throw new IllegalArgumentException("Invalid data");
        }
        PatientBinaryFileHandler.serializePatients(patients, filename);
    }


    public void saveToDB(){
        try {
            InputStream input = new FileInputStream("src/config.properties");
            Properties prop = new Properties();
            prop.load(input);
            String DBusername = prop.getProperty("DBusername");
            String DBpassword = prop.getProperty("DBpassword");
            String DBname = prop.getProperty("DBname");
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + DBname, DBusername, DBpassword);
            String query = "INSERT patients (id, name, age, phone_number) VALUES (?, ?, ?, ?)"+
                    "ON DUPLICATE KEY UPDATE name = ?, age = ?, phone_number = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            for(Patient patient : patients){
                preparedStatement.setInt(1, patient.getID());
                preparedStatement.setString(2, patient.getName());
                preparedStatement.setInt(3, patient.getAge());
                preparedStatement.setString(4, patient.getPhone_number());
                preparedStatement.setString(5, patient.getName());
                preparedStatement.setInt(6, patient.getAge());
                preparedStatement.setString(7, patient.getPhone_number());
                preparedStatement.executeUpdate();

            }
            con.close();

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void readFromDB(){
        try {
            InputStream input = new FileInputStream("config.properties");
            Properties prop = new Properties();
            prop.load(input);
            String DBusername = prop.getProperty("DBusername");
            String DBpassword = prop.getProperty("DBpassword");
            String DBname = prop.getProperty("DBname");
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + DBname, DBusername, DBpassword);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from doctors");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String phone_number = rs.getString("phone_number");
                patients.add(new Patient(id, name,  phone_number,age));
            }

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
