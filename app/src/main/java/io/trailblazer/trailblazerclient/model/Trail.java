/*
 * This work is Copyright, 2019, Isaac Lindland, Joel Bond, Khizar Saleem, Justin Dominguez
 * All rights reserved
 */

package io.trailblazer.trailblazerclient.model;

import com.google.gson.annotations.Expose;
import java.io.Serializable;

/**
 * The type Trail.
 */
public class Trail implements Serializable {


  private static final long serialVersionUID = -6806226205580239636L;

  @Expose
  private long id;

  @Expose
  private String name;

  @Expose
  private String description;

  @Expose
  private String imageUrl;

  @Expose
  private User creator;

  @Expose
  private Geometry geometry;


  /**
   * Gets id.
   *
   * @return the id
   */
  public long getId() {
    return id;
  }

  /**
   * Sets id.
   *
   * @param id the id
   */
  public void setId(long id) {
    this.id = id;
  }

  /**
   * Gets name.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets name.
   *
   * @param name the name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets description.
   *
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Sets description.
   *
   * @param description the description
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Gets image url.
   *
   * @return the image url
   */
  public String getImageUrl() {
    return imageUrl;
  }

  /**
   * Sets image url.
   *
   * @param imageUrl the image url
   */
  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  /**
   * Gets creator.
   *
   * @return the creator
   */
  public User getCreator() {
    return creator;
  }

  /**
   * Sets creator.
   *
   * @param creator the creator
   */
  public void setCreator(User creator) {
    this.creator = creator;
  }

  /**
   * Gets geometry.
   *
   * @return the geometry
   */
  public Geometry getGeometry() {
    return geometry;
  }

  /**
   * Sets geometry.
   *
   * @param geometry the geometry
   */
  public void setGeometry(Geometry geometry) {
    this.geometry = geometry;
  }


  @Override
  public String toString() {
    return "Trail{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", description='" + description + '\'' +
        ", imageUrl='" + imageUrl + '\'' +
        ", creator=" + creator +
        ", geometry=" + geometry +
        '}';
  }
}
