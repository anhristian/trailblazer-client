package io.trailblazer.trailblazerclient.service;

import android.app.Application;
import io.trailblazer.trailblazerclient.model.User;
import io.trailblazer.trailblazerclient.model.UserCharacteristics;

public class TrailsDatabase {

  TrailsDatabase() {
  }

  private static Application applicationContext;

  public static void setApplicationContext(Application applicationContext) {
    TrailsDatabase.applicationContext = applicationContext;
  }

  public static void main(String[] args) {
    UserCharacteristics user = new UserCharacteristics();
    user.setUser(new User("bobthebuilder"));
    NetworkService.getInstance().updateUser("asdasd", user);
  }
}
