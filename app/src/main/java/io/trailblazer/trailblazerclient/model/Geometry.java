/*
 * This work is Copyright, 2019, Isaac Lindland, Joel Bond, Khizar Saleem, Justin Dominguez
 * All rights reserved
 */

package io.trailblazer.trailblazerclient.model;

import com.google.gson.annotations.Expose;

public class Geometry {

  @Expose
  private String type;

  @Expose
  private double[][] coordinates;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public double[][] getCoordinates() {
    return coordinates;
  }

  public void setCoordinates(double[][] coordinates) {
    this.coordinates = coordinates;
  }

}
