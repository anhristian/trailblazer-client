package io.trailblazer.trailblazerclient.service;


import android.app.Application;
import android.content.Context;
import android.os.Vibrator;

public class MapsService {

  private static Application applicationContext;
  private static Vibrator vibrator;

  private MapsService() {
    vibrator = (Vibrator) applicationContext.getSystemService(Context.VIBRATOR_SERVICE);
  }

  /**
   * Sets the context required by any services used by this service.
   *
   * @param applicationContext {@link android.content.Context} used for signing in.
   */
  public static void setApplicationContext(Application applicationContext) {
    MapsService.applicationContext = applicationContext;
  }

  public static MapsService getInstance() {
    return MapsService.InstanceHolder.INSTANCE;
  }

  private static class InstanceHolder {

    private static final MapsService INSTANCE = new MapsService();
  }

}
