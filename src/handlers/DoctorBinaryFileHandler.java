package handlers;

import domain.Doctor;

import java.io.*;
import java.util.ArrayList;

public class DoctorBinaryFileHandler {
    public static void serializeDoctors(ArrayList<Doctor> doctors, String filename) {
        ObjectOutputStream out = null;
        try
        {
            out = new ObjectOutputStream(new FileOutputStream(filename));
            out.writeObject(doctors);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static ArrayList<Doctor> desirializeDoctors(String filename){
        ObjectInputStream in = null;
        ArrayList<Doctor> list = null;
        try
        {
            in = new ObjectInputStream(new FileInputStream(filename));
            list = (ArrayList<Doctor>) in.readObject();
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
