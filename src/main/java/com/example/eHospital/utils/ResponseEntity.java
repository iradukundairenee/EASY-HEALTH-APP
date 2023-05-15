package com.example.eHospital.utils;

public class ResponseEntity<T> {
  String message;
  T payload;

  public ResponseEntity(String message, T payload) {
    this.message = message;
    this.payload = payload;
  }

  public ResponseEntity() {
    this.message = "Server Error!";
    this.payload = null;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public T getPayload() {
    return payload;
  }

  public void setPayload(T payload) {
    this.payload = payload;
  }

}
