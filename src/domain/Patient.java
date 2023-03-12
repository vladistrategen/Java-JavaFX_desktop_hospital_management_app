package domain;

import repository.AppointmentRepo;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;

public class Patient implements Entity, Serializable {
    private String SerializerUID = "1L";

    private int ID;
    private String name;
    private String phone_number;
    private int age;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone_number;
    }
    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return ID == patient.ID && age == patient.age && Objects.equals(name, patient.name) && Objects.equals(phone_number, patient.phone_number);
    }

    public Patient(int ID, String name, String phone_number, int age) {
        this.ID = ID;
        this.name = name;
        this.phone_number = phone_number;
        this.age = age;
    }



    @Override
    public String toString() {
        return "Patient{" +
                "ID=" + ID +
                "\n\tname='" + name + '\'' +
                "\n\tphone_number='" + phone_number + '\'' +
                "\n\tage=" + age +
                '}'+"\n";
    }

    public void update(Patient patient) {
        this.ID = patient.ID;
        this.name = patient.name;
        this.phone_number = patient.phone_number;
        this.age = patient.age;
    }

    public ArrayList<Appointment> getAppointments(AppointmentRepo ar) {
        ArrayList<Appointment> appointments = new ArrayList<>();
        for (Appointment a : ar.getAppointments()) {
            if (a.getPatient().equals(this)) {
                appointments.add(a);
            }
        }
        return appointments;
    }
}
