package com.example.eHospital.user.servlets;

import java.io.IOException;

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

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            String requestBody = RequestUtil.getBody(req);
            String role = RequestUtil.getKeyFromJson(requestBody, "role");
            User user = null;

            switch (role) {
                case "Patient":
                    user = new JSONUtil().fromJson(requestBody, Patient.class);
                    ResponseEntity<User> patientResults = ((Patient) user).register();
                    ResFormat.res(res, patientResults, HttpServletResponse.SC_CREATED);
                    break;
                case "Physician":
                    user = new JSONUtil().fromJson(requestBody, Physician.class);
                    ResponseEntity<User> physicianResults = ((Physician) user).register();
                    ResFormat.res(res, physicianResults, HttpServletResponse.SC_CREATED);
                    break;
                case "Pharmacist":
                    user = new JSONUtil().fromJson(requestBody, Pharmacist.class);
                    ResponseEntity<User> pharmacistResults = ((Pharmacist) user).register();
                    ResFormat.res(res, pharmacistResults, HttpServletResponse.SC_CREATED);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid user role: " + role);
            }

        } catch (Exception e) {
            e.printStackTrace();
            ResFormat.res(res, new ResponseEntity<>(e.getMessage(), null), HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
