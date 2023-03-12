package repository;
import domain.Doctor;
import handlers.DoctorBinaryFileHandler;
import handlers.DoctorTxtFileHandler;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.sql.*;
import java.util.Properties;

public class DoctorRepo implements GenericRepo {
    private ArrayList<Doctor> doctors;


    public DoctorRepo() {
        this.doctors = new ArrayList<Doctor>();


    }

    public void add(Object o) {
        doctors.add((Doctor) o);
    }

    public void remove(Object o) {
        doctors.remove(o);
    }

    public void remove(int ID) {
        for (Doctor doctor : doctors) {
            if (doctor.getID() == ID) {
                doctors.remove(doctor);
                return;
            }
        }
    }

    public Doctor getDoctor(int ID) {
        Doctor result = null;
        for (Doctor doctor : doctors) {
            if (doctor.getID() == ID) {
                return doctor;
            }
        }
        return result;
    }

    public void update(int ID, Object newobj) {
        Doctor updated = (Doctor) newobj;
        doctors.get(ID).update(updated);
    }

    public void print() {
        for (Doctor doctor : doctors) {
            System.out.println(doctor);
        }
    }

    public boolean exists(Object o) {
        for(Doctor doctor : doctors) {
            if(doctor.getID() == ((Doctor) o).getID()) {
                return true;
            }
        }
        return false;
    }

    public void sort() {
        doctors.sort((o1, o2) -> o1.getName().compareTo(o2.getName()));
    }

    public ArrayList<Doctor> getDoctors() {
        return doctors;
    }

    @Override
    public String toString() {
        String result= "";
        result+= "Doctors: ";
        for(Doctor doctor : doctors) {
            result+= doctor.toString();
        }
        return result;
    }

    public void read_doctors_txt(String filename) {
        if (filename == null || filename.isBlank() || filename.isEmpty()) {
            throw new IllegalArgumentException("Invalid data");
        }
        doctors = DoctorTxtFileHandler.createDoctors(filename);
    }

    public void write_doctors_txt(String filename) {
        if (filename == null ) {
            throw new IllegalArgumentException("Invalid data");
        }
        DoctorTxtFileHandler.writeDoctors( doctors,filename);
    }

    public void read_doctors_binary(String filename) {
        if (filename == null || filename.isBlank() || filename.isEmpty()) {
            throw new IllegalArgumentException("Invalid data");
        }
        doctors = DoctorBinaryFileHandler.desirializeDoctors(filename);
    }

    public void write_doctors_binary(String filename) {
        if (filename == null ) {
            throw new IllegalArgumentException("Invalid data");
        }
        DoctorBinaryFileHandler.serializeDoctors(doctors, filename);
    }

    public void saveToDB(){
        try{
            InputStream input = new FileInputStream("src/config.properties");
            Properties prop = new Properties();
            prop.load(input);
            String DBusername = prop.getProperty("DBusername");
            String DBpassword = prop.getProperty("DBpassword");
            String DBname = prop.getProperty("DBname");

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + DBname, DBusername, DBpassword);
            String stmt = "INSERT INTO doctors (id, name, specialization, salary) VALUES (?,?,?,?) " +
                    "ON DUPLICATE KEY UPDATE name = ?, specialization = ?, salary = ?";
            PreparedStatement preparedStatement = con.prepareStatement(stmt);
            for(Doctor doctor : doctors){
                preparedStatement.setInt(1,doctor.getID());
                preparedStatement.setString(2,doctor.getName());
                preparedStatement.setString(3,doctor.getSpecialization());
                preparedStatement.setDouble(4,doctor.getSalary());
                preparedStatement.setString(5,doctor.getName());
                preparedStatement.setString(6,doctor.getSpecialization());
                preparedStatement.setDouble(7,doctor.getSalary());
                preparedStatement.executeUpdate();
            }
            con.close();

        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public void readFromDB(){
        try{
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
            while(rs.next()){
                Doctor doctor = new Doctor(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getDouble(4));
                doctors.add(doctor);
            }
            con.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

}
