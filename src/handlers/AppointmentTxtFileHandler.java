package handlers;

import domain.Appointment;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

import repository.*;


public class AppointmentTxtFileHandler {

    public static void writeAppointments(ArrayList<Appointment> appointments, String filename) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(filename));
            for (Appointment a : appointments) {
                bw.write(a.getID() + " | " + a.getDoctor().getID() + " | " + a.getPatient().getID() + " | " + a.getDate().toString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static ArrayList<Appointment> createAppointments(PatientRepo prepo, DoctorRepo drepo, String filename) {
        ArrayList<Appointment> appointments = new ArrayList<>();
        BufferedReader br = null;

        try
        {
            br = new BufferedReader(new FileReader(filename));
            String line = null;
            while ((line = br.readLine()) != null)
            {
                String[] elems = line.split("[|]");
                if (elems.length < 4)
                    continue;
                String[] date_elems = elems[3].split("[-]");
                LocalDate date = LocalDate.of(Integer.parseInt(date_elems[0].strip()),
                        Integer.parseInt(date_elems[1].strip()),
                        Integer.parseInt(date_elems[2].strip()));
                Appointment b = new Appointment(
                        Integer.parseInt(elems[0].strip()),
                        drepo.getDoctor(Integer.parseInt(elems[1].strip())),
                        prepo.getPatient(Integer.parseInt(elems[2].strip())),
                        date);
                appointments.add(b);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (br != null)
                try {
                    br.close();
                }
                catch (IOException e)
                {
                    System.out.println("Error while closing the file " + e);
                }
        }
        return appointments;
    }

}
