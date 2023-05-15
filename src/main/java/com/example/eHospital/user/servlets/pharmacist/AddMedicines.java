package com.example.eHospital.user.servlets.pharmacist;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.eHospital.database.MedicineDB;
import com.example.eHospital.database.PharmacistDB;
import com.example.eHospital.user.models.Medicine;
import com.example.eHospital.user.models.Pharmacist;
import com.example.eHospital.utils.JSONUtil;
import com.example.eHospital.utils.JwtUtil;
import com.example.eHospital.utils.ResFormat;
import com.example.eHospital.utils.ResponseEntity;

import io.jsonwebtoken.Claims;

@WebServlet("/pharmacist/addMedicine")
public class AddMedicines extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        try {
            String jwtToken = JwtUtil.extractToken(req);
            Claims claims = JwtUtil.parseJwtToken(jwtToken);

            String pharmacistPhone = claims.get("phone", String.class);
            Pharmacist pharmacist = PharmacistDB.findPharmacist(pharmacistPhone);

            if (pharmacist == null) {
                throw new IllegalArgumentException("Unauthorized");
            }

            Medicine med = new JSONUtil().parseBodyJson(req, Medicine.class);
            ResponseEntity<Medicine> results = MedicineDB.addMedicine(med);
            ResFormat.res(res, results, HttpServletResponse.SC_OK);

        } catch (Exception e) {
            e.printStackTrace();
            ResFormat.res(res, new ResponseEntity<>(e.getMessage(), null), HttpServletResponse.SC_FORBIDDEN);
        }

    }
}
