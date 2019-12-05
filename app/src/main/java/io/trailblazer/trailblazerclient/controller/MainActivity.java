/*
 * This work is Copyright, 2019, Isaac Lindland, Joel Bond, Khizar Saleem, Justin Dominguez
 * All rights reserved
 */

package io.trailblazer.trailblazerclient.controller;

import android.Manifest.permission;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import io.trailblazer.trailblazerclient.R;
import io.trailblazer.trailblazerclient.service.GoogleSignInService;
import io.trailblazer.trailblazerclient.viewmodel.TrailViewModel;
import io.trailblazer.trailblazerclient.viewmodel.UserViewModel;

/**
 * The type Main activity.
 */
public class MainActivity extends AppCompatActivity
    implements BottomNavigationView.OnNavigationItemSelectedListener {

  private static final String TAG = "main_activity";
  private static final int PERMISSIONS_REQUEST_CODE = 1000;
  private GoogleSignInService signInService;
  private TrailViewModel trailViewModel;
  private UserViewModel userViewModel;
  private ActionBar actionBar;
  private int currentFragment;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_main);
    setupViewModel();
    setupSignIn();
    Log.d(TAG, "onCreate: setting up sign-in");
    BottomNavigationView navigation = findViewById(R.id.navigation);
    navigation.setOnNavigationItemSelectedListener(this);
    navigation.setSelectedItemId(R.id.nav_explore_item);
    actionBar = getActionBar();
    ActivityCompat.requestPermissions(this,
        new String[]{
            permission.ACCESS_FINE_LOCATION,
        }, PERMISSIONS_REQUEST_CODE);

  }

  private void setupViewModel() {
    trailViewModel = ViewModelProviders.of(this).get(TrailViewModel.class);
    userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
    getLifecycle().addObserver(trailViewModel);
    getLifecycle().addObserver(userViewModel);

  }

  private void setupSignIn() {
    signInService = GoogleSignInService.getInstance();
    signInService.getAccount().observe(this, (account) -> {
      trailViewModel.setAccount(account);
      userViewModel.requestUserCharacteristics();
        }
    );
  }

  @Override
  public boolean onNavigationItemSelected(@NonNull MenuItem item) {

    if (item.getItemId() == currentFragment) {
      return true;
    }

    switch (item.getItemId()) {
      case R.id.nav_explore_item:

        Navigation.findNavController(this, R.id.container_fragment).navigate(R.id.trail_viewer_nav);
        getSupportActionBar().hide();
        currentFragment = R.id.nav_explore_item;
        return true;
      case R.id.nav_map_item:
        Navigation.findNavController(this, R.id.container_fragment).navigate(R.id.map_nav);
        getSupportActionBar().hide();
        currentFragment = R.id.nav_map_item;
        return true;
      case R.id.nav_profile_item:
        Navigation.findNavController(this, R.id.container_fragment).navigate(R.id.profile_nav);
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().show();
        currentFragment = R.id.nav_profile_item;

        return true;
    }
    return false;
  }

//  @Override
//  public boolean onCreateOptionsMenu(Menu menu) {
//    getMenuInflater().inflate(R.menu.menu_main, menu);
//    return true;
//  }

//  @Override
//  public boolean onOptionsItemSelected(MenuItem item) {
//    boolean handled = true;
//    switch (item.getItemId()) {
//      case R.id.edit:
//        return false;
//      case R.id.action_settings:
//        break;
//      case R.id.sign_out:
//        signOut();
//        break;
//      default:
//        handled = super.onOptionsItemSelected(item);
//    }
//    return handled;
//  }


  private void signOut() {
    signInService.signOut()
        .addOnCompleteListener((task) -> {
          Intent intent = new Intent(this, LoginActivity.class);
          intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
          startActivity(intent);
        });
  }

  /**
   * Refresh's sign in.
   *
   * @param runnable the runnable
   */
  public void refreshSignIn(Runnable runnable) {
    signInService.refresh()
        .addOnSuccessListener((account) -> runnable.run())
        .addOnFailureListener((e) -> signOut());
  }



}
