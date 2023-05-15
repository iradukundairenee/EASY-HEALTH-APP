package com.example.eHospital.user.models;

import java.util.UUID;
import javax.naming.AuthenticationException;

import com.example.eHospital.user.dtos.Gender;
import com.example.eHospital.user.dtos.UserRoles;
import com.example.eHospital.utils.ResponseEntity;
import com.google.gson.annotations.Expose;

public abstract class User {
  public String id;
  public String fullNames;
  private Gender gender;
  private Integer age;
  protected UserRoles role;

  @Expose(serialize = false)
  private String password;

  public User(String id, String fullNames, Gender gender, Integer age, UserRoles role, String password) {
    this.id = UUID.randomUUID().toString();
    this.age = age;
    this.fullNames = fullNames;
    this.gender = gender;
    this.role = role;
    this.password = password;
  }

  public abstract ResponseEntity<User> register() throws Exception;

  public abstract ResponseEntity<String> login(String email, String Password) throws AuthenticationException;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getFullNames() {
    return fullNames;
  }

  public void setFullNames(String fullNames) {
    this.fullNames = fullNames;
  }

  public Gender getGender() {
    return gender;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public UserRoles getRole() {
    return role;
  }

  public void setRole(UserRoles role) {
    this.role = role;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}