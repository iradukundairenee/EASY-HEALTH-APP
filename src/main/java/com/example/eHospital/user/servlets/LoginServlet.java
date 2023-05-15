package com.example.eHospital.user.servlets;

import java.io.IOException;

import javax.naming.AuthenticationException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.eHospital.user.models.Patient;
import com.example.eHospital.user.models.Pharmacist;
import com.example.eHospital.user.models.Physician;
import com.example.eHospital.user.models.User;
import com.example.eHospital.utils.JSONUtil;
import com.example.eHospital.utils.RequestUtil;
import com.example.eHospital.utils.ResFormat;
import com.example.eHospital.utils.ResponseEntity;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            String requestBody = RequestUtil.getBody(req);
            String role = RequestUtil.getKeyFromJson(requestBody, "role");

            switch (role) {
                case "Patient":
                    Patient patient = new JSONUtil().fromJson(requestBody, Patient.class);
                    ResponseEntity<String> patientResults = patient.login(patient.getUsername(), patient.getPassword());
                    ResFormat.res(res, patientResults, HttpServletResponse.SC_OK);
                    break;
                case "Physician":
                    Physician physician = new JSONUtil().fromJson(requestBody, Physician.class);
                    ResponseEntity<String> physicianResults = physician.login(physician.getEmail(),
                            physician.getPassword());
                    ResFormat.res(res, physicianResults, HttpServletResponse.SC_OK);
                    break;
                case "Pharmacist":
                    Pharmacist pharmacist = new JSONUtil().fromJson(requestBody, Pharmacist.class);
                    ResponseEntity<String> pharmacistResults = pharmacist.login(pharmacist.getPhone(),
                            pharmacist.getPassword());
                    ResFormat.res(res, pharmacistResults, HttpServletResponse.SC_OK);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid user role: " + role);
            }

        } catch (AuthenticationException e) {
            e.printStackTrace();
            ResFormat.res(res, new ResponseEntity<>(e.getMessage(), null), HttpServletResponse.SC_FORBIDDEN);
        } catch (Exception e) {
            e.printStackTrace();
            ResFormat.res(res, new ResponseEntity<>(e.getMessage(), null), HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
