package com.example.eHospital.user.models;

public class Consultation {
  private String disease;
  private Physician physician;

  public Consultation(String disease, Physician physician) {
    this.disease = disease;
    this.physician = physician;
  }

  public String getDisease() {
    return disease;
  }

  public void setDisease(String disease) {
    this.disease = disease;
  }

  public Physician getPhysician() {
    return physician;
  }

  public void setPhysician(Physician physician) {
    this.physician = physician;
  }

}