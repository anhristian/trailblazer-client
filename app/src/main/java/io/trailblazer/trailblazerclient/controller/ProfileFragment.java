package io.trailblazer.trailblazerclient.controller;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import io.trailblazer.trailblazerclient.R;
import io.trailblazer.trailblazerclient.model.UserCharacteristics;
import io.trailblazer.trailblazerclient.viewmodel.UserViewModel;


/**
 * The type Profile fragment.
 */
public class ProfileFragment extends Fragment {


  private static final String TAG = "ProfileFragment";
  private View view;
  private UserViewModel userViewModel;
  private UserCharacteristics userCharacteristics;
  private ImageView editProfile;
  private EditText editUsername;
  private EditText editFirstName;
  private EditText editLastName;
  private EditText editAge;
  private EditText editWeight;
  private EditText editHeight;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    view = inflater.inflate(R.layout.profile_fragment, container, false);
    initViews();
    setEditable(false);

    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
    userViewModel.getUserCharacteristic().observe(this, this::populateFields);
    userViewModel.requestUserCharacteristics();
    userViewModel.getThrowable().observe(this, throwable -> {
      Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_LONG).show();
    });

  }

  private void initViews() {
    editProfile = view.findViewById(R.id.edit);
    editUsername = view.findViewById(R.id.edit_username);
    editFirstName = view.findViewById(R.id.edit_first_name);
    editLastName = view.findViewById(R.id.edit_last_name);
    editAge = view.findViewById(R.id.edit_age);
    editWeight = view.findViewById(R.id.edit_weight);
    editHeight = view.findViewById(R.id.edit_height);
  }

  private void setEditable(boolean editable) {

    editUsername.setEnabled(editable);
    editFirstName.setEnabled(editable);
    editLastName.setEnabled(editable);
    editAge.setEnabled(editable);
    editWeight.setEnabled(editable);
    editHeight.setEnabled(editable);
  }

  private void populateFields(UserCharacteristics userCharacteristics) {
    Log.d(TAG, "populateFields: " + userCharacteristics);

    if (userCharacteristics.getUsername() != null) {
      editUsername.setText(userCharacteristics.getUsername());
    }

    if (userCharacteristics.getFirstName() != null) {
      editFirstName.setText(userCharacteristics.getFirstName());
    }

    if (userCharacteristics.getLastName() != null) {
      editLastName.setText(userCharacteristics.getLastName());
    }

    if (userCharacteristics.getAge() != null) {
      editAge.setText(userCharacteristics.getAge().toString());
    }

    if (userCharacteristics.getWeightLbs() != null) {
      editWeight.setText(userCharacteristics.getWeightLbs().toString());
    }

    if (userCharacteristics.getHeightInches() != null) {
      editHeight.setText(userCharacteristics.getHeightInches().toString());
    }
  }


}
