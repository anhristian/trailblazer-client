/*
 * This work is Copyright, 2019, Isaac Lindland, Joel Bond, Khizar Saleem, Justin Dominguez
 * All rights reserved
 */

package io.trailblazer.trailblazerclient.model;

import com.google.gson.annotations.Expose;

/**
 * The type Geometry.
 */
public class Geometry {

  @Expose
  private String type;

  @Expose
  private double[][] coordinates;

  /**
   * Gets type.
   *
   * @return the type
   */
  public String getType() {
    return type;
  }

  /**
   * Sets type.
   *
   * @param type the type
   */
  public void setType(String type) {
    this.type = type;
  }

  /**
   * Get coordinates double [ ] [ ].
   *
   * @return the double [ ] [ ]
   */
  public double[][] getCoordinates() {
    return coordinates;
  }

  /**
   * Sets coordinates.
   *
   * @param coordinates the coordinates
   */
  public void setCoordinates(double[][] coordinates) {
    this.coordinates = coordinates;
  }

}
