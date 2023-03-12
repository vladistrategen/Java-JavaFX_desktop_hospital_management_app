package handlers;

import domain.Appointment;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentBinaryFileHandler {
    public static void serializeAppointments(ArrayList<Appointment> appointments, String filename) {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new FileOutputStream(filename));
            out.writeObject(appointments);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static ArrayList<Appointment> deserializeAppointments(String filename) {
        ObjectInputStream in = null;
        ArrayList<Appointment> list = null;
        try
        {
            in = new ObjectInputStream(new FileInputStream(filename));
            list = (ArrayList<Appointment>) in.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
