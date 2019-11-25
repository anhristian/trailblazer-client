package io.trailblazer.trailblazerclient.controller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import io.trailblazer.trailblazerclient.R;
import io.trailblazer.trailblazerclient.service.GoogleSignInService;
import io.trailblazer.trailblazerclient.viewmodel.MainViewModel;
import io.trailblazer.trailblazerclient.viewmodel.TrailViewViewModel;

public class MainActivity extends AppCompatActivity
    implements BottomNavigationView.OnNavigationItemSelectedListener {

  private static final String TAG = "main_activity";
  private MainViewModel viewModel;
  private GoogleSignInService signInService;
  private TrailViewViewModel trailViewViewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    setupSignIn();
    Log.d(TAG, "onCreate: setting up sign-in");
    BottomNavigationView navigation = findViewById(R.id.navigation);
    navigation.setOnNavigationItemSelectedListener(this);
    setupViewModel();
  }

  private void setupViewModel() {
    trailViewViewModel = ViewModelProviders.of(this).get(TrailViewViewModel.class);
    viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
  }


  @Override
  public boolean onNavigationItemSelected(@NonNull MenuItem item) {

    switch (item.getItemId()) {
      case R.id.nav_explore_item:
        Navigation.findNavController(this, R.id.container_fragment).navigate(R.id.trail_viewer_nav);
        return true;
      case R.id.nav_map_item:
        Navigation.findNavController(this, R.id.container_fragment).navigate(R.id.map_nav);
        return true;

    }
    return false;
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    boolean handled = true;
    switch (item.getItemId()) {
      case R.id.action_settings:
        break;
      case R.id.sign_out:
        signOut();
        break;
      default:
        handled = super.onOptionsItemSelected(item);
    }
    return handled;
  }

  private void setupSignIn() {
    signInService = GoogleSignInService.getInstance();

    signInService.getAccount().observe(this, (account) -> {
          trailViewViewModel.setAccount(account);
          viewModel.setAccount(account);
          viewModel.printOauth();
        }

    );
  }

  private void signOut() {
    signInService.signOut()
        .addOnCompleteListener((task) -> {
          Intent intent = new Intent(this, LoginActivity.class);
          intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
          startActivity(intent);
        });
  }

  private void refreshSignIn(Runnable runnable) {
    signInService.refresh()
        .addOnSuccessListener((account) -> runnable.run())
        .addOnFailureListener((e) -> signOut());
  }

}
