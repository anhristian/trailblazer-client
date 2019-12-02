package io.trailblazer.trailblazerclient.model;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.util.Date;

public class UserCharacteristics implements Serializable {


  private static final long serialVersionUID = -4786245411663138394L;

  @Expose
  private User user;

  @Expose
  private Date created;

  @Expose
  private Date updated;

  @Expose
  private Double weightLbs;

  @Expose
  private Double heightInches;

  @Expose
  private String firstName;

  @Expose
  private String lastName;


  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  public Date getUpdated() {
    return updated;
  }

  public void setUpdated(Date updated) {
    this.updated = updated;
  }

  public Double getWeightLbs() {
    return weightLbs;
  }

  public void setWeightLbs(Double weightLbs) {
    this.weightLbs = weightLbs;
  }

  public Double getHeightInches() {
    return heightInches;
  }

  public void setHeightInches(Double heightInches) {
    this.heightInches = heightInches;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
}
