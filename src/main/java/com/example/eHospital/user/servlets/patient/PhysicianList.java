package com.example.eHospital.user.servlets.patient;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.eHospital.database.PhysicianDB;
import com.example.eHospital.user.models.Physician;
import com.example.eHospital.user.models.User;
import com.example.eHospital.utils.ResFormat;
import com.example.eHospital.utils.ResponseEntity;

@WebServlet("/physicians")
public class PhysicianList extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            Physician[] physicians = PhysicianDB.getPhysicians();

            // sort last names - ascending
            Arrays.sort(physicians, new Comparator<User>() {
                @Override
                public int compare(User physician1, User physician2) {
                    return physician1.getFullNames().compareTo(physician2.getFullNames());
                }
            });

            ResFormat.res(res, new ResponseEntity<User[]>("physicians retrieved successfully", physicians),
                    HttpServletResponse.SC_OK);
        } catch (Exception e) {
            e.printStackTrace();
            ResFormat.res(res, new ResponseEntity<>(e.getMessage(), null), HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
