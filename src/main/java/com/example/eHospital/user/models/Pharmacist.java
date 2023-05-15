package com.example.eHospital.user.models;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.naming.AuthenticationException;

import com.example.eHospital.database.PharmacistDB;
import com.example.eHospital.user.dtos.Gender;
import com.example.eHospital.user.dtos.UserRoles;
import com.example.eHospital.utils.PasswordUtil;
import com.example.eHospital.utils.ResponseEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class Pharmacist extends User {

    private String phone;

    public Pharmacist(String id, String fullNames, Gender gender, Integer age, UserRoles role, String password) {
        super(id, fullNames, gender, age, role, password);
        // TODO Auto-generated constructor stub
    }

    @Override
    public ResponseEntity<User> register() throws Exception {
        if (!PasswordUtil.isValidPassword(getPassword(), 9, 10)) {
            throw new Exception("Pharmacist's password must be 9-10 characters");
        }

        PharmacistDB.addPharmacist(this);

        return new ResponseEntity<User>("Pharmacist registered successfully!",
                PharmacistDB.findPharmacist(getPhone()));
    }

    @Override
    public ResponseEntity<String> login(String phone, String Password) throws AuthenticationException {
        Pharmacist existingUser = PharmacistDB.findPharmacist(phone);

        if (existingUser == null)
            throw new AuthenticationException("Pharmacist does not exist!");

        if (!Password.equals(existingUser.getPassword()))
            throw new AuthenticationException("Incorrect phone number or password!");

        String secretKey = "mysecretkeywhichmustnotbelessthan256bitslong";
        Key signingKey = new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS256.getJcaName());

        Claims claims = Jwts.claims().setSubject(existingUser.phone);
        claims.put("role", existingUser.role.name());
        claims.put("phone", existingUser.phone);

        Instant now = Instant.now();

        String jwtToken = Jwts.builder()
                .claim("phone", existingUser.phone)
                .claim("role", existingUser.role.name())
                .setSubject(existingUser.phone)
                .setId(existingUser.id)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(10l, ChronoUnit.HOURS)))
                .signWith(signingKey)
                .compact();

        return new ResponseEntity<String>("Logged in Successfully!", jwtToken);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
