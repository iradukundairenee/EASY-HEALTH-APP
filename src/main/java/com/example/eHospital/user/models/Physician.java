package com.example.eHospital.user.models;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.naming.AuthenticationException;

import com.example.eHospital.database.PhysicianDB;
import com.example.eHospital.user.dtos.Gender;
import com.example.eHospital.user.dtos.UserRoles;
import com.example.eHospital.utils.PasswordUtil;
import com.example.eHospital.utils.ResponseEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class Physician extends User {

    private String email;

    public Physician(String fullNames, String email, String password, String id, Integer age, Gender gender,
            UserRoles role) {
        super(id, fullNames, gender, age, role, password);
        this.email = email;
    }

    @Override
    public ResponseEntity<User> register() throws Exception {

        if (!PasswordUtil.isValidPassword(getPassword(), 7, 8)) {
            throw new Exception("Physician's password must be 7-8 characters");
        }

        PhysicianDB.addPhysician(this);
        return new ResponseEntity<User>("Physician registered successfully!", PhysicianDB.findPhysician(getEmail()));
    }

    @Override
    public ResponseEntity<String> login(String email, String Password) throws AuthenticationException {
        Physician existingUser = PhysicianDB.findPhysician(email);

        if (existingUser == null)
            throw new AuthenticationException("Physician does not exist!");

        if (!Password.equals(existingUser.getPassword()))
            throw new AuthenticationException("Incorrect email or password!");

        String secretKey = "mysecretkeywhichmustnotbelessthan256bitslong";
        Key signingKey = new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS256.getJcaName());

        Claims claims = Jwts.claims().setSubject(existingUser.email);
        claims.put("role", existingUser.role.name());
        claims.put("email", existingUser.email);

        Instant now = Instant.now();

        String jwtToken = Jwts.builder()
                .claim("email", existingUser.email)
                .claim("role", existingUser.role.name())
                .setSubject(existingUser.email)
                .setId(existingUser.id)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(10l, ChronoUnit.HOURS)))
                .signWith(signingKey)
                .compact();

        return new ResponseEntity<String>("Logged in Successfully!", jwtToken);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
