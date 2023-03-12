package service;
import domain.Appointment;
import repository.AppointmentRepo;
import repository.DoctorRepo;
import repository.PatientRepo;

import java.time.LocalDate;
import java.util.ArrayList;

public class AppointmentController {
    private AppointmentRepo appointmentRepo;

    public AppointmentController(AppointmentRepo appointmentRepo) {
        this.appointmentRepo = appointmentRepo;
    }


    public void addAppointment(Appointment appointment) {
        if (appointment.getDoctor() == null || appointment.getPatient() == null || appointment.getDate() == null) {
            throw new IllegalArgumentException("Invalid data");
        }
        appointmentRepo.add(appointment);
    }

    public void removeAppointment(Appointment appointment) {
        try {
            appointmentRepo.remove(appointment);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid data");
        }

    }
    public void removeAppointment(int ID) {
        try {
            appointmentRepo.remove(ID);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid data");
        }

    }

    public void updateAppointment(Appointment old, Appointment updated) {
        if(!appointmentRepo.exists(old)) {
            throw new IllegalArgumentException("Invalid data");
        }
        if (old.getDoctor() == null || old.getPatient() == null || old.getDate() == null) {
            throw new IllegalArgumentException("Invalid data");
        }
        if (updated.getDoctor() == null || updated.getPatient() == null || updated.getDate() == null) {
            throw new IllegalArgumentException("Invalid data");
        }
        appointmentRepo.update(old.getID(), updated);
    }

    public void updateAppointment(int ID, Appointment updated) {
        if (updated.getDoctor() == null || updated.getPatient() == null || updated.getDate() == null) {
            throw new IllegalArgumentException("Invalid data");
        }
        for (Appointment appointment : appointmentRepo.getAppointments()) {
            if (appointment.getID() == ID) {
                appointment.update(updated);
                return;
            }
        }
        throw new IllegalArgumentException("Invalid data");
    }

    public void printAppointments() {
        appointmentRepo.print();
    }

    public void sortAppointments() {
        if(appointmentRepo.getAppointments().size() == 0) {
            throw new IllegalArgumentException("Repo is empty");
        }
        appointmentRepo.sort();
    }

    public AppointmentRepo filterByDate(LocalDate date) {
        AppointmentRepo filtered = new AppointmentRepo();
        for (Appointment appointment : appointmentRepo.getAppointments()) {
            if (appointment.getDate().equals(date)) {
                filtered.add(appointment);
            }
        }
        return filtered;
    }

    public AppointmentRepo filterByDoctor(int doctorID) {
        AppointmentRepo filtered = new AppointmentRepo();
        for (Appointment appointment : appointmentRepo.getAppointments()) {
            if (appointment.getDoctor().getID() == doctorID) {
                filtered.add(appointment);
            }
        }
        return filtered;
    }

    public AppointmentRepo filterByPatient(int patientID) {
        AppointmentRepo filtered = new AppointmentRepo();
        for (Appointment appointment : appointmentRepo.getAppointments()) {
            if (appointment.getPatient().getID() == patientID) {
                filtered.add(appointment);
            }
        }
        return filtered;
    }

    public void read_appointments_txt(PatientRepo prepo, DoctorRepo drepo, String filename) {
        try {
            appointmentRepo.read_appointments_txt(prepo, drepo, filename);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void write_appointments_txt(String filename) {
        try {
            appointmentRepo.write_appointments_txt(filename);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void read_appointments_binary(String filename) {
        try {
            appointmentRepo.read_appointments_binary(filename);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void write_appointments_binary(String filename) {
        try {
            appointmentRepo.write_appointments_binary(filename);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Appointment> getAllInAday(LocalDate date) {
        return appointmentRepo.getAllInADay(date);
    }
    public AppointmentRepo getAppointmentRepo() {
        return appointmentRepo;
    }

    @Override
    public String toString() {
        return appointmentRepo.toString();
    }

    public void writeToDB() {
        appointmentRepo.saveToDB();
    }
}
