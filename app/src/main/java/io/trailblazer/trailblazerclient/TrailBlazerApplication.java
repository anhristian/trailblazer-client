/*
 * This work is Copyright, 2019, Isaac Lindland, Joel Bond, Khizar Saleem, Justin Dominguez
 * All rights reserved
 */

package io.trailblazer.trailblazerclient;

import android.app.Application;
import com.squareup.picasso.Picasso;
import io.trailblazer.trailblazerclient.service.GoogleSignInService;
import io.trailblazer.trailblazerclient.service.LocatorService;

public class TrailBlazerApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    LocatorService.setApplicationContext(this);
    GoogleSignInService.setApplicationContext(this);
    Picasso.setSingletonInstance(new Picasso.Builder(this)
        .loggingEnabled(true).build());


  }

}
