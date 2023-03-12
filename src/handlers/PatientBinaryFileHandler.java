package handlers;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import domain.*;

public class PatientBinaryFileHandler {
    public static void serializePatients(List<Patient> patients, String filename)
    {
        ObjectOutputStream out = null;
        try
        {
            out = new ObjectOutputStream(new FileOutputStream(filename));
            out.writeObject(patients);

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

    public static ArrayList<Patient> deserializePatients(String filename)
    {
        ObjectInputStream in = null;
        ArrayList<Patient> list = null;
        try
        {
            in = new ObjectInputStream(new FileInputStream(filename));
            list = (ArrayList<Patient>) in.readObject();
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
