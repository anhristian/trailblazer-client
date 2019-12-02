package io.trailblazer.trailblazerclient.service;

import android.app.Application;

public class TrailsDatabase {


  private static Application applicationContext;

  public static void setApplicationContext(Application applicationContext) {
    TrailsDatabase.applicationContext = applicationContext;
  }

}
