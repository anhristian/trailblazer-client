/*
 * This work is Copyright, 2019, Isaac Lindland, Joel Bond, Khizar Saleem, Justin Dominguez
 * All rights reserved
 */

package io.trailblazer.trailblazerclient.model;

import com.google.gson.annotations.Expose;
import java.util.Date;

/**
 * The type User characteristics.
 */

public class UserCharacteristics {

  @Expose
  private String username;

  @Expose
  private User user;

  @Expose
  private Date created;

  @Expose
  private Double weightLbs;

  @Expose
  private Double heightInches;

  @Expose
  private String firstName;

  @Expose
  private String lastName;

  @Expose
  private Integer age;

  /**
   * Gets user.
   *
   * @return the user
   */
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public User getUser() {
    return user;
  }

  /**
   * Sets user.
   *
   * @param user the user
   */
  public void setUser(User user) {
    this.user = user;
  }

  /**
   * Gets created.
   *
   * @return the created
   */
  public Date getCreated() {
    return created;
  }


  /**
   * Gets updated.
   *
   * @return the updated
   */

  public void setCreated(Date created) {
    this.created = created;
  }

  /**
   * Gets weight lbs.
   *
   * @return the weight lbs
   */
  public Double getWeightLbs() {
    return weightLbs;
  }

  /**
   * Sets weight lbs.
   *
   * @param weightLbs the weight lbs
   */
  public void setWeightLbs(Double weightLbs) {
    this.weightLbs = weightLbs;
  }

  /**
   * Gets height inches.
   *
   * @return the height inches
   */
  public Double getHeightInches() {
    return heightInches;
  }

  /**
   * Sets height inches.
   *
   * @param heightInches the height inches
   */
  public void setHeightInches(Double heightInches) {
    this.heightInches = heightInches;
  }

  /**
   * Gets first name.
   *
   * @return the first name
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * Sets first name.
   *
   * @param firstName the first name
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * Gets last name.
   *
   * @return the last name
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * Sets last name.
   *
   * @param lastName the last name
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * Gets age.
   *
   * @return the age
   */
  public Integer getAge() {
    return age;
  }

  /**
   * Sets age.
   *
   * @param age the age
   */
  public void setAge(Integer age) {
    this.age = age;
  }
}
