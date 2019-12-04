/*
 * This work is Copyright, 2019, Isaac Lindland, Joel Bond, Khizar Saleem, Justin Dominguez
 * All rights reserved
 */

package io.trailblazer.trailblazerclient.model;

import com.google.gson.annotations.Expose;
import java.io.Serializable;

/**
 * The type User.
 */
public class User implements Serializable {


  private static final long serialVersionUID = -6806721301874662419L;

  @Expose
  private String username;

  /**
   * Instantiates a new User.
   */
  public User() {
  }

  /**
   * Instantiates a new User.
   *
   * @param username the username
   */
  public User(String username) {
    this.username = username;
  }

  /**
   * Gets username.
   *
   * @return the username
   */
  public String getUsername() {
    return username;
  }

  /**
   * Sets username.
   *
   * @param username the username
   */
  public void setUsername(String username) {
    this.username = username;
  }
}
