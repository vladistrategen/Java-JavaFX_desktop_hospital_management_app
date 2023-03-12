package handlers;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import domain.*;

public class PatientTxtFileHandler  {
    public static ArrayList<Patient> createPatients(String filename) {
        ArrayList<Patient> patients = new ArrayList<Patient>();
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
                Patient b = new Patient(Integer.parseInt(elems[0].strip()), elems[1].strip(),
                        elems[2].strip(),Integer.parseInt(elems[3].strip()));
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

    public static void writePatients(ArrayList<Patient> books, String filename)
    {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(filename));
            for (Patient p: books)
            {
                bw.write(p.getID() + " | " + p.getName() + " | " + p.getAge() + " | " + p.getPhone_number());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try
            {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
