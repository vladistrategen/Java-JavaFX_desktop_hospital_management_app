package domain;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Doctor implements Entity, Serializable {
    private String SerializerUID = "2L";

    private int ID;
    private String name;
    private String spec;
    private double salary;



    public Doctor(int ID, String name, String spec, double salary) {
        this.ID = ID;
        this.name = name;
        this.spec = spec;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "ID=" + ID +
                "\n\tname='" + name + '\'' +
                "\n\tspec='" + spec + '\'' +
                "\n\tsalary=" + salary +
                '}'+"\n";
    }

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

    public String getSpecialization() {
        return spec;
    }

    public void getSpecialization(String spec) {
        this.spec = spec;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void update(Doctor updated) {
        this.name = updated.name;
        this.spec = updated.spec;
        this.salary = updated.salary;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Doctor doctor = (Doctor) o;
        return ID == doctor.ID && Double.compare(doctor.salary, salary) == 0 && Objects.equals(SerializerUID, doctor.SerializerUID) && Objects.equals(name, doctor.name) && Objects.equals(spec, doctor.spec);
    }


}
