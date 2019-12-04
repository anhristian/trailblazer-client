/*
 * This work is Copyright, 2019, Isaac Lindland, Joel Bond, Khizar Saleem, Justin Dominguez
 * All rights reserved
 */

package io.trailblazer.trailblazerclient.service;

import android.app.Application;

/**
 * The type Trails database.
 */
public class TrailsDatabase {


  private static Application applicationContext;

  /**
   * Sets application context.
   *
   * @param applicationContext the application context
   */
  public static void setApplicationContext(Application applicationContext) {
    TrailsDatabase.applicationContext = applicationContext;
  }

}
