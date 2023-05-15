package com.example.eHospital.user.models;

public class Medicine {
  private String medName;
  private double medPrice;
  private String expirationDate;

  public Medicine(String medName, double medPrice, String expirationDate) {
    this.medName = medName;
    this.medPrice = medPrice;
    this.expirationDate = expirationDate;
  }

  public String getMedName() {
    return medName;
  }

  public void setMedName(String medName) {
    this.medName = medName;
  }

  public double getMedPrice() {
    return medPrice;
  }

  public void setMedPrice(double medPrice) {
    this.medPrice = medPrice;
  }

  public String getExpirationDate() {
    return expirationDate;
  }

  public void setExpirationDate(String expirationDate) {
    this.expirationDate = expirationDate;
  }

}
