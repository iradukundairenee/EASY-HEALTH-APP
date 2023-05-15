package com.example.eHospital.database;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.example.eHospital.user.models.Consultation;
import com.example.eHospital.user.models.Patient;
import com.example.eHospital.user.models.Pharmacist;
import com.example.eHospital.user.models.Physician;
import com.example.eHospital.user.models.Prescription;

public class PatientDB {
    private static Map<String, Patient> patients = new LinkedHashMap<>();

    public static void addPatient(Patient patient) {
        if (patients.get(patient.getUsername()) != null) {
            throw new RuntimeException("Patient already Exists");
        }
        patients.put(patient.getUsername(), patient);
    }

    public static Patient findPatient(String username) {
        return patients.get(username);
    }

    public static Patient selectPhysician(String username, Physician selectedPhysician) throws Exception {
        Patient patient = patients.get(username);
        patient.setSelectedPhysician(selectedPhysician);
        patients.put(username, patient);

        return patient;
    }

    public static Patient selectPharmacist(String username, Pharmacist selectedPharmacist)
            throws Exception {
        Patient patient = patients.get(username);
        patient.setSelectedPharmacist(selectedPharmacist);
        patients.put(username, patient);

        return patient;
    }

    public static Patient getConsultation(String username, Consultation consultation)
            throws Exception {
        Patient patient = patients.get(username);
        patient.setConsultation(consultation);
        patients.put(username, patient);

        return patient;
    }

    public static Patient getPrescription(String username, Prescription prescription)
            throws Exception {
        Patient patient = patients.get(username);
        patient.setPrescription(prescription);
        patients.put(username, patient);

        return patient;
    }

    public static Patient[] getPatients() {
        return patients.values().toArray(new Patient[patients.size()]);
    }

    public static Patient[] getPhysicianPatients(String physicianEmail) {
        List<Patient> matchingPatients = new ArrayList<>();
        for (Patient patient : patients.values()) {
            Physician physician = patient.getSelectedPhysician();
            if (physician instanceof Physician && physician.getEmail().equals(physicianEmail)) {
                matchingPatients.add(patient);
            }
        }
        Patient[] result = new Patient[matchingPatients.size()];
        return matchingPatients.toArray(result);
    }

    public static Patient[] getPharmacistPatientsWhoHaveConsultation(String pharmacistPhone) {
        List<Patient> matchingPatients = new ArrayList<>();
        for (Patient patient : patients.values()) {
            if (patient.getConsultation() != null) {
                Pharmacist pharmacist = patient.getSelectedPharmacist();
                if (pharmacist instanceof Pharmacist && pharmacist.getPhone().equals(pharmacistPhone)) {
                    matchingPatients.add(patient);
                }
            }
        }
        Patient[] result = new Patient[matchingPatients.size()];
        return matchingPatients.toArray(result);
    }

}
