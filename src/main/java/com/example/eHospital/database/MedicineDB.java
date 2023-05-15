package com.example.eHospital.database;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import com.example.eHospital.user.models.Medicine;
import com.example.eHospital.utils.ResponseEntity;

public class MedicineDB {
    private static final String FILE_NAME = "medicines.csv";
    private static final String[] HEADER = { "med_name", "med_price", "med_expiration" };
    private static Map<String, Medicine> medicines = new LinkedHashMap<>();

    public static ResponseEntity<Medicine> addMedicine(Medicine med) {
        if (medicines.get(med.getMedName()) != null) {
            throw new RuntimeException("Medicine with the same name already exists.");
        }
        medicines.put(med.getMedName(), med);
        saveToFile();
        return new ResponseEntity<Medicine>("Medicine added successfully", med);
    }

    public static Medicine[] getMedicines() {
        Collection<Medicine> medValues = medicines.values();
        return medValues.toArray(new Medicine[medicines.size()]);
    }

    public static Medicine findMedicine(String medName) {
        return medicines.get(medName);
    }

    private static void saveToFile() {
        File file = new File(FILE_NAME);
        FileWriter writer = null;

        try {
            // Create the file if it does not exist
            if (!file.exists()) {
                file.createNewFile();
            }

            writer = new FileWriter(file);

            // Write the header row
            for (int i = 0; i < HEADER.length; i++) {
                writer.append(HEADER[i]);
                if (i < HEADER.length - 1) {
                    writer.append(",");
                }
            }
            writer.append("\n");

            // Write the medicines
            for (Map.Entry<String, Medicine> entry : medicines.entrySet()) {
                writer.append(entry.getValue().getMedName());
                writer.append(",");
                writer.append(Double.toString(entry.getValue().getMedPrice()));
                writer.append(",");
                writer.append(entry.getValue().getExpirationDate());
                writer.append("\n");
            }

            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
