package com.example.eHospital.user.models;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.naming.AuthenticationException;

import com.example.eHospital.database.PatientDB;
import com.example.eHospital.user.dtos.Gender;
import com.example.eHospital.user.dtos.UserRoles;
import com.example.eHospital.utils.PasswordUtil;
import com.example.eHospital.utils.ResponseEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class Patient extends User {
  private String username;
  private Physician selectedPhysician;
  private Pharmacist selectedPharmacist;
  private Consultation consultation;
  private Prescription prescription;

  public Patient(String id, String fullNames, Gender gender, Integer age, UserRoles role, String password) {
    super(id, fullNames, gender, age, role, password);
  }

  @Override
  public ResponseEntity<User> register() throws Exception {

    if (!PasswordUtil.isValidPassword(getPassword(), 4, 6)) {
      throw new Exception("Patient's password must be 4-6 characters");
    }

    PatientDB.addPatient(this);
    return new ResponseEntity<User>("Patient registered successfully!", PatientDB.findPatient(getUsername()));
  }

  @Override
  public ResponseEntity<String> login(String username, String Password) throws AuthenticationException {
    Patient existingUser = PatientDB.findPatient(username);

    if (existingUser == null)
      throw new AuthenticationException("Patient does not exist!");

    if (!Password.equals(existingUser.getPassword()))
      throw new AuthenticationException("Incorrect username or password!");

    Claims claims = Jwts.claims().setSubject(existingUser.username);
    claims.put("role", existingUser.role.name());
    claims.put("username", existingUser.username);

    Instant now = Instant.now();
    Instant expirationTime = now.plus(10, ChronoUnit.HOURS);

    // Get the signing key
    String secretKey = "mysecretkeywhichmustnotbelessthan256bitslong";
    Key signingKey = new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS256.getJcaName());

    // Build the JWT token
    String jwtToken = Jwts.builder()
        .setSubject(existingUser.username)
        .claim("username", existingUser.username)
        .claim("role", existingUser.role.name())
        .setIssuedAt(Date.from(now))
        .setExpiration(Date.from(expirationTime))
        .signWith(signingKey)
        .compact();

    return new ResponseEntity<String>("Logged in Successfully!", jwtToken);
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Physician getSelectedPhysician() {
    return selectedPhysician;
  }

  public void setSelectedPhysician(Physician selectedPhysician) {
    this.selectedPhysician = selectedPhysician;
  }

  public Pharmacist getSelectedPharmacist() {
    return selectedPharmacist;
  }

  public void setSelectedPharmacist(Pharmacist selectedPharmacist) {
    this.selectedPharmacist = selectedPharmacist;
  }

  public Consultation getConsultation() {
    return consultation;
  }

  public void setConsultation(Consultation consultation) {
    this.consultation = consultation;
  }

  public Prescription getPrescription() {
    return prescription;
  }

  public void setPrescription(Prescription prescription) {
    this.prescription = prescription;
  }

}
