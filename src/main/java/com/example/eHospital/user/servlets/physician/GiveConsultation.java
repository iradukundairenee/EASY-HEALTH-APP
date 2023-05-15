package com.example.eHospital.user.servlets.physician;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.eHospital.database.PatientDB;
import com.example.eHospital.database.PhysicianDB;
import com.example.eHospital.user.models.Consultation;
import com.example.eHospital.user.models.Patient;
import com.example.eHospital.user.models.Physician;
import com.example.eHospital.utils.JwtUtil;
import com.example.eHospital.utils.RequestUtil;
import com.example.eHospital.utils.ResFormat;
import com.example.eHospital.utils.ResponseEntity;

import io.jsonwebtoken.Claims;

@WebServlet("/physician/giveConsultation")
public class GiveConsultation extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            String requestBody = RequestUtil.getBody(req);
            String disease = RequestUtil.getKeyFromJson(requestBody, "disease");
            String patientUsername = RequestUtil.getKeyFromJson(requestBody, "patientUsername");

            String jwtToken = JwtUtil.extractToken(req);
            Claims claims = JwtUtil.parseJwtToken(jwtToken);

            String physicianEmail = claims.get("email", String.class);
            Physician physician = PhysicianDB.findPhysician(physicianEmail);

            Consultation consultation = new Consultation(disease, physician);

            Patient patient = PatientDB.findPatient(patientUsername);

            if (physician == null) {
                ResFormat.res(res, new ResponseEntity<>("401 Unauthorized", null), HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            if (patient == null) {
                throw new IllegalArgumentException("Patient not found");
            }

            if (patient.getSelectedPhysician() != physician) {
                ResFormat.res(res,
                        new ResponseEntity<>("401 Unauthorized, Only a selected physician can give consultation", null),
                        HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            Patient result = PatientDB.getConsultation(patientUsername, consultation);
            ResFormat.res(res, new ResponseEntity<Patient>("successfully given consultation to patient", result),
                    HttpServletResponse.SC_OK);

        } catch (Exception e) {
            e.printStackTrace();
            ResFormat.res(res, new ResponseEntity<>(e.getMessage(), null), HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
