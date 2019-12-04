/*
 * This work is Copyright, 2019, Isaac Lindland, Joel Bond, Khizar Saleem, Justin Dominguez
 * All rights reserved
 */

package io.trailblazer.trailblazerclient.service;

import android.app.Application;
import android.content.Context;
import android.os.Vibrator;

/**
 * The type Map service.
 */
public class MapService {

  private static Application applicationContext;
  private static Vibrator vibrator;

  private MapService() {
    vibrator = (Vibrator) applicationContext.getSystemService(Context.VIBRATOR_SERVICE);
  }

  /**
   * Sets application context.
   *
   * @param applicationContext the application context
   */
  public static void setApplicationContext(Application applicationContext) {
    MapService.applicationContext = applicationContext;
  }

  /**
   * Gets instance.
   *
   * @return the instance
   */
  public static MapService getInstance() {
    return MapService.InstanceHolder.INSTANCE;
  }





  private static class InstanceHolder {

    private static final MapService INSTANCE = new MapService();
  }

}
