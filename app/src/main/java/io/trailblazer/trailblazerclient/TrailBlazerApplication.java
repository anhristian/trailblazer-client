package io.trailblazer.trailblazerclient;

import android.app.Application;
import com.squareup.picasso.Picasso;
import io.trailblazer.trailblazerclient.service.GoogleSignInService;

public class TrailBlazerApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    GoogleSignInService.setApplicationContext(this);
    Picasso.setSingletonInstance(new Picasso.Builder(this)
        .loggingEnabled(true).build());


  }

}
