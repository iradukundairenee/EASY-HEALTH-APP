package com.example.eHospital.database;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import com.example.eHospital.user.models.Physician;

public class PhysicianDB {
    private static Map<String, Physician> physicians = new LinkedHashMap<>();

    public static void addPhysician(Physician physician) {
        if (physicians.get(physician.getEmail()) != null) {
            throw new RuntimeException("Physician already Exists");
        }
        physicians.put(physician.getEmail(), physician);
    }

    public static Physician findPhysician(String email) {
        return physicians.get(email);
    }

    public static Physician[] getPhysicians() {
        Collection<Physician> physicianValues = physicians.values();
        return physicianValues.toArray(new Physician[physicians.size()]);
    }
}
