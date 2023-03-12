package domain;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;

public class Appointment implements Entity, Serializable {

    private String SerializerUID = "3L";

    private int ID;

    private Doctor doctor;
    private Patient patient;
    private LocalDate date;


    public int getDID() {
        return doctor.getID();
    }

    public int getPID() {
        return patient.getID();
    }


    public Appointment(int ID, Doctor doctor, Patient patient, LocalDate date) {
        this.ID = ID;
        this.doctor = doctor;
        this.patient = patient;
        this.date = date;
    }

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public void setID(int ID) {
        this.ID = ID;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void update(Appointment appointment) {
        this.ID = appointment.ID;
        this.doctor = appointment.doctor;
        this.patient = appointment.patient;
        this.date = appointment.date;
    }

    public String toString() {
        return "Appointment{" +
                "ID=" + ID +
                "\n\tdoctor=" + doctor +
                "\n\tpatient=" + patient +
                "\n\tdate=" + date +
                "\n"+'}' + "";
    }

    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Appointment that = (Appointment) o;
        return ID == that.ID && doctor.equals(that.doctor) && patient.equals(that.patient) && date.equals(that.date);
    }

    public int compareTo(Appointment o) {
        return this.date.compareTo(o.date);
    }
}
