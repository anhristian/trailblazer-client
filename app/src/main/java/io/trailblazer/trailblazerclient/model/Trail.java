package io.trailblazer.trailblazerclient.model;

import com.google.gson.annotations.Expose;
import java.io.Serializable;

public class Trail implements Serializable {

  private static final long serialVersionUID = -6009093971113874346L;

  @Expose
  private long id;

  @Expose
  private String trails;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getTrails() {
    return trails;
  }

  public void setTrails(String trails) {
    this.trails = trails;
  }
}
