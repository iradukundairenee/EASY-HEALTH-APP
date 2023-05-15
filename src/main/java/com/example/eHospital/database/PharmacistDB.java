package com.example.eHospital.database;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import com.example.eHospital.user.models.Pharmacist;

public class PharmacistDB {
    private static Map<String, Pharmacist> pharmacists = new LinkedHashMap<>();

    public static void addPharmacist(Pharmacist pharmacist) {
        if (pharmacists.get(pharmacist.getPhone()) != null) {
            throw new RuntimeException("Pharmacist already Exists");
        }
        pharmacists.put(pharmacist.getPhone(), pharmacist);
    }

    public static Pharmacist findPharmacist(String phone) {
        return pharmacists.get(phone);
    }

    public static Pharmacist[] getPharmacists() {
        Collection<Pharmacist> pharmacistValues = pharmacists.values();
        return pharmacistValues.toArray(new Pharmacist[pharmacists.size()]);
    }

}
