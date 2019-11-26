package io.trailblazer.trailblazerclient.model;

import com.google.gson.annotations.Expose;
import java.io.Serializable;

public class User implements Serializable {


  private static final long serialVersionUID = -6806721301874662419L;

  @Expose
  private String username;


  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
