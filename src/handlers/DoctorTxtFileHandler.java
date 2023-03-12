package handlers;

import domain.Doctor;

import javax.print.Doc;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorTxtFileHandler {
    public static ArrayList<Doctor> createDoctors(String filename) {
        ArrayList<Doctor> patients = new ArrayList<Doctor>();
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
                Doctor b = new Doctor(Integer.parseInt(elems[0].strip()), elems[1].strip(),
                        elems[2].strip(),Double.parseDouble(elems[3].strip()));
                patients.add(b);
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
        return patients;
    }

    public static void writeDoctors(ArrayList<Doctor> doctors, String filename) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(filename));
            for (Doctor d : doctors) {
                bw.write(d.getID() + " | " + d.getName() + " | " + d.getSpecialization() + " | " + d.getSalary());
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
}
