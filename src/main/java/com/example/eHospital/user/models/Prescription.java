package com.example.eHospital.user.models;

public class Prescription {
  private String disease;
  private Medicine medicine;

  public Prescription(String disease, Medicine medicine) {
    this.disease = disease;
    this.medicine = medicine;
  }

  public String getDisease() {
    return disease;
  }

  public void setDisease(String disease) {
    this.disease = disease;
  }

  public Medicine getMedicine() {
    return medicine;
  }

  public void setMedicine(Medicine medicine) {
    this.medicine = medicine;
  }

}
