package io.trailblazer.trailblazerclient;

import android.app.Application;
import com.squareup.picasso.Picasso;
import io.trailblazer.trailblazerclient.service.GoogleSignInService;
import io.trailblazer.trailblazerclient.service.LocationService;

public class TrailBlazerApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    LocationService.setApplicationContext(this);
    GoogleSignInService.setApplicationContext(this);
    Picasso.setSingletonInstance(new Picasso.Builder(this)
        .loggingEnabled(true).build());


  }

}
