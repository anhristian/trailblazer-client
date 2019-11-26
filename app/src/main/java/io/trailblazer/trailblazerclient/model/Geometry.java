package io.trailblazer.trailblazerclient.model;

import com.google.gson.annotations.Expose;
import java.util.Arrays;

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


  @Override
  public String toString() {
    return "Geometry{" +
        "type='" + type + '\'' +
        ", coordinates=" + Arrays.toString(coordinates[1]) +
        '}';
  }
}
