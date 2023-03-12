package service;
import domain.*;
import repository.*;
public class PatientController {
    private PatientRepo patientRepo;

    public PatientController(PatientRepo patientRepo) {
        this.patientRepo = patientRepo;
    }

    public void addPatient(Patient patient) {
        if (patient.getName() == null || patient.getAge() == 0) {
            throw new IllegalArgumentException("Invalid data");
        }
        patientRepo.add(patient);
    }

    public void removePatient(Patient patient) {
        try {
            patientRepo.remove(patient);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid data");
        }

    }
    public void removePatient(int ID) {
        try {
            patientRepo.remove(ID);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid data");
        }

    }

    public void updatePatient(Patient old, Patient updated) {
        if(!patientRepo.exists(old)) {
            throw new IllegalArgumentException("Invalid data");
        }
        if (old.getName() == null || old.getAge() == 0) {
            throw new IllegalArgumentException("Invalid data");
        }
        if (updated.getName() == null || updated.getAge() == 0) {
            throw new IllegalArgumentException("Invalid data");
        }
        patientRepo.update(old.getID(), updated);
    }

    public Patient getPatient(int ID) {
        Patient result = null;
        result= patientRepo.getPatient(ID);
        if(result == null) {
            throw new IllegalArgumentException("Invalid data");
        }
        return result;
    }

    public void updatePatient(int ID, Patient updated) {
        if (updated.getName() == null || updated.getAge() == 0) {
            throw new IllegalArgumentException("Invalid data");
        }
        for (Patient patient : patientRepo.getPatients()) {
            if (patient.getID() == ID) {
                this.patientRepo.update(ID,updated);
                return;
            }
        }
        throw new IllegalArgumentException("Invalid data");
    }

    public void read_patients_txt(String filename) {
        try {
            patientRepo.read_patients_txt(filename);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid data");
        }
    }

    public PatientRepo getPatientRepo() {
        return patientRepo;
    }

    public void write_patients_txt(String filename) {
        // check if file exists or is empty and throw exception

        try {
            patientRepo.write_patients_txt(filename);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void read_patients_binary(String filename) {
        try {
            patientRepo.read_patients_binary(filename);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void write_patients_binary(String filename) {
        try {
            patientRepo.write_patients_binary(filename);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public String toString() {
        return patientRepo.toString();
    }

    public void writeToDB() {
        patientRepo.saveToDB();
    }
}
