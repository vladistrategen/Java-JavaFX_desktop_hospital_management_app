package repository;
import domain.Doctor;
import domain.Patient;
import handlers.*;

import domain.Appointment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.sql.*;
import java.util.stream.Collectors;

public class AppointmentRepo implements GenericRepo{
    private ArrayList<Appointment> appointments;

    public AppointmentRepo() {
        this.appointments = new ArrayList<Appointment>();
    }

    public void add(Object o) {
        appointments.add((Appointment) o);
    }

    public void remove(Object o) {
        appointments.remove(o);
    }

    public void remove(int ID) {
        for (Appointment appointment : appointments) {
            if (appointment.getID() == ID) {
                appointments.remove(appointment);
                return;
            }
        }
    }
    public void update(int ID, Object newobj) {
        Appointment updated = (Appointment) newobj;
        getAppointment(ID).update(updated);
    }

    public Appointment getAppointment(int ID) {
        Appointment result = null;
        for (Appointment appointment : appointments) {
            if (appointment.getID() == ID) {
                return appointment;
            }
        }
        return result;
    }

    public void print() {
        for (Appointment appointment : appointments) {
            System.out.println(appointment);
        }
    }

    public boolean exists(Object o) {
        for(Appointment appointment : appointments) {
            if(appointment.getID() == ((Appointment) o).getID()) {
                return true;
            }
        }
        return false;
    }

    public void sort() {
        // sort by date
        Collections.sort(appointments, (o1, o2) -> o1.getDate().compareTo(o2.getDate()));
    }

    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    public AppointmentRepo filterByDoctor(int doctorID) {
        AppointmentRepo filtered = new AppointmentRepo();
        for (Appointment appointment : appointments) {
            if (appointment.getDoctor().getID() == doctorID) {
                filtered.add(appointment);
            }
        }
        return filtered;
    }

    public AppointmentRepo filterByPatient(int patientID) {
        AppointmentRepo filtered = new AppointmentRepo();
        for (Appointment appointment : appointments) {
            if (appointment.getPatient().getID() == patientID) {
                filtered.add(appointment);
            }
        }
        return filtered;
    }

    public AppointmentRepo filterByDate(LocalDate date) {
        AppointmentRepo filtered = new AppointmentRepo();
        for (Appointment appointment : appointments) {
            if (appointment.getDate().equals(date)) {
                filtered.add(appointment);
            }
        }
        return filtered;
    }


    @Override
    public String toString() {
        String result= "";
        result+= "Appointments: ";
        for (Appointment appointment : appointments) {
            result += appointment.toString() + "\n" ;
        }
        return result;

    }

    public void read_appointments_txt(PatientRepo patientRepo, DoctorRepo doctorRepo, String filename) {
        if(patientRepo == null || doctorRepo == null || patientRepo.getPatients().isEmpty() || doctorRepo.getDoctors().isEmpty()) {
            System.out.println("PatientRepo or DoctorRepo is null");
            return;
        }
        if(filename == null || filename.isBlank() || filename.isEmpty()) {
            throw new IllegalArgumentException("Invalid data");
        }
        appointments = AppointmentTxtFileHandler.createAppointments( patientRepo, doctorRepo, filename);
    }

    public void write_appointments_txt(String filename) {
        if(filename == null ) {
            throw new IllegalArgumentException("Invalid data");
        }
        AppointmentTxtFileHandler.writeAppointments(appointments, filename);
    }

    public void write_appointments_binary(String filename) {
        if(filename == null || filename.isBlank() || filename.isEmpty()) {
            throw new IllegalArgumentException("Invalid data");
        }
        AppointmentBinaryFileHandler.serializeAppointments(appointments, filename);
    }

    public void read_appointments_binary(String filename) {
        if(filename == null ) {
            throw new IllegalArgumentException("Invalid data");
        }
        appointments = AppointmentBinaryFileHandler.deserializeAppointments(filename);
    }

    public boolean equals(AppointmentRepo other){
        if(other == null){
            return false;
        }
        if(this.appointments.size() != other.appointments.size()){
            return false;
        }
        for(int i = 0; i < this.appointments.size(); i++){
            if(!this.appointments.get(i).equals(other.appointments.get(i))){
                return false;
            }
        }
        return true;
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
            String stmt = "INSERT INTO appointments (id, doctor_id, patient_id, date) VALUES (?, ?, ?, ?)" +
                    "ON DUPLICATE KEY UPDATE doctor_id = ?, patient_id = ?, date = ?";
            PreparedStatement ps = con.prepareStatement(stmt);
            for( Appointment a : appointments){
                ps.setInt(1, a.getID());
                ps.setInt(2, a.getDoctor().getID());
                ps.setInt(3, a.getPatient().getID());
                ps.setDate(4, Date.valueOf(a.getDate()));
                ps.setInt(5, a.getDoctor().getID());
                ps.setInt(6, a.getPatient().getID());
                ps.setDate(7, Date.valueOf(a.getDate()));
                ps.executeUpdate();
            }
            con.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void readFromDB(DoctorRepo dr, PatientRepo pr){
        try {
            InputStream input = new FileInputStream("config.properties");
            Properties prop = new Properties();
            prop.load(input);
            String DBusername = prop.getProperty("DBusername");
            String DBpassword = prop.getProperty("DBpassword");
            String DBname = prop.getProperty("DBname");
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + DBname, DBusername, DBpassword);
            String stmt = "SELECT * FROM appointments";
            PreparedStatement ps = con.prepareStatement(stmt);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Appointment a = new Appointment(rs.getInt("id"), dr.getDoctor(rs.getInt("doctor_id")), pr.getPatient(rs.getInt("patient_id")), rs.getDate("date").toLocalDate());
                add(a);
            }
            con.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Appointment> getAllInADay(LocalDate date){
        return appointments.stream().filter(a -> a.getDate().equals(date)).collect(Collectors.toCollection(ArrayList::new));
    }
    public ArrayList<Appointment> getAllForPatient(Patient p){
        return appointments.stream().filter(a -> a.getPatient().equals(p)).collect(Collectors.toCollection(ArrayList::new));
    }
    public ArrayList<Appointment> getAllForDoctor(Doctor d){
        return appointments.stream().filter(a -> a.getDoctor().equals(d)).collect(Collectors.toCollection(ArrayList::new));
    }
    public ArrayList<Appointment> getForCardiologists( String specialization) {
        return appointments.stream().filter(a -> a.getDoctor().getSpecialization().equals(specialization)).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Patient> getPatientsWithoutAppointments(){
        ArrayList<Patient> patients = new ArrayList<>();
        for(Appointment a : appointments){
            patients.add(a.getPatient());
        }
        return patients.stream().distinct().collect(Collectors.toCollection(ArrayList::new));
    }

}




