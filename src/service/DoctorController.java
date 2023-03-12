package service;
import domain.*;
import repository.*;

public class DoctorController {
private DoctorRepo doctorRepo;

    public DoctorController(DoctorRepo doctorRepo) {
        this.doctorRepo = doctorRepo;
    }

    public void addDoctor(Doctor doctor) {
        if (doctor.getName() == null || doctor.getSpecialization() == null) {
            throw new IllegalArgumentException("Invalid data");
        }
        doctorRepo.add(doctor);
    }

    public void removeDoctor(Doctor doctor) {
        try {
            doctorRepo.remove(doctor);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid data");
        }

    }
    public void removeDoctor(int ID) {
        try {
            doctorRepo.remove(ID);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid data");
        }

    }

    public void updateDoctor(Doctor old, Doctor updated) {
        if(!doctorRepo.exists(old)) {
            throw new IllegalArgumentException("Invalid data");
        }
        if (old.getName() == null || old.getSpecialization() == null) {
            throw new IllegalArgumentException("Invalid data");
        }
        if (updated.getName() == null || updated.getSpecialization() == null) {
            throw new IllegalArgumentException("Invalid data");
        }
        doctorRepo.update(old.getID(), updated);
    }

    public Doctor getDoctor(int ID) {
        Doctor result = null;
        result= doctorRepo.getDoctor(ID);
        if(result == null) {
            throw new IllegalArgumentException("Invalid data");
        }
        return result;
    }

    public void updateDoctor(int ID, Doctor updated) {
        if (updated.getName() == null || updated.getSpecialization() == null) {
            throw new IllegalArgumentException("Invalid data");
        }
        for (Doctor doctor : doctorRepo.getDoctors()) {
            if (doctor.getID() == ID) {
                doctor.update(updated);
                return;
            }
        }
        throw new IllegalArgumentException("Invalid data");
    }

    public void write_doctors_txt(String filename) {
        try{
            doctorRepo.write_doctors_txt(filename);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void read_doctors_txt(String filename) {
        try {
            doctorRepo.read_doctors_txt(filename);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void write_doctors_binary(String filename) {
        try {
            doctorRepo.write_doctors_binary(filename);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void read_doctors_binary(String filename) {
        try {
            doctorRepo.read_doctors_binary(filename);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void writeToDB() {
        doctorRepo.saveToDB();
    }

    public void readFromDB() {
        doctorRepo.readFromDB();
    }

    public DoctorRepo getDoctorRepo() {
        return doctorRepo;
    }

    public String toString() {
        return doctorRepo.toString();
    }
}
