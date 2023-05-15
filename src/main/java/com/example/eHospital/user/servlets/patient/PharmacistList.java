package com.example.eHospital.user.servlets.patient;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.eHospital.database.PharmacistDB;
import com.example.eHospital.user.models.Pharmacist;
import com.example.eHospital.user.models.User;
import com.example.eHospital.utils.ResFormat;
import com.example.eHospital.utils.ResponseEntity;

@WebServlet("/pharmacists")
public class PharmacistList extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            Pharmacist[] pharmacists = PharmacistDB.getPharmacists();

            // sort age - descending
            Arrays.sort(pharmacists, new Comparator<User>() {
                @Override
                public int compare(User pharmacist1, User pharmacist2) {
                    return Integer.compare(pharmacist2.getAge(), pharmacist1.getAge());
                }
            });

            ResFormat.res(res, new ResponseEntity<User[]>("pharmacists retrieved successfully", pharmacists),
                    HttpServletResponse.SC_OK);
        } catch (Exception e) {
            e.printStackTrace();
            ResFormat.res(res, new ResponseEntity<>(e.getMessage(), null), HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
