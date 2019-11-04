package io.trailblazer.trailblazerclient.controller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import io.trailblazer.trailblazerclient.R;
import io.trailblazer.trailblazerclient.service.GoogleSignInService;
import io.trailblazer.trailblazerclient.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity
    implements BottomNavigationView.OnNavigationItemSelectedListener {

  private MainViewModel viewModel;
  private GoogleSignInService signInService;
  private TextView mTextMessage;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    setupSignIn();
    mTextMessage = findViewById(R.id.message);
    BottomNavigationView navigation = findViewById(R.id.navigation);
    navigation.setOnNavigationItemSelectedListener(this);
    setupViewModel();
  }
  private void setupViewModel() {
    viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
  }


  @Override
  public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    viewModel.printOauth();
    switch (item.getItemId()) {



      case R.id.navigation_home:
        mTextMessage.setText(R.string.title_home);
        return true;
      case R.id.navigation_dashboard:
        mTextMessage.setText(R.string.title_dashboard);
        return true;
      case R.id.navigation_notifications:
        mTextMessage.setText(R.string.title_notifications);
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
    // FIXME Re-enable after view model implemented.

    signInService.getAccount().observe(this, (account) ->
        viewModel.setAccount(account));
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
