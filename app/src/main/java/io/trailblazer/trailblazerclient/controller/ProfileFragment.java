package io.trailblazer.trailblazerclient.controller;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import io.trailblazer.trailblazerclient.R;
import io.trailblazer.trailblazerclient.model.User;
import io.trailblazer.trailblazerclient.model.UserCharacteristics;
import io.trailblazer.trailblazerclient.viewmodel.UserViewModel;


public class ProfileFragment extends Fragment {


  private View view;
  private Context context;
  private UserViewModel userViewModel;
  private User user;
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
    userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
    view = inflater.inflate(R.layout.profile_fragment, container, false);
    context = container.getContext();
    initViews();
    setEditable(false);
    userViewModel.requestUserCharacteristics();
    userViewModel.getUserCharacteristic().observe(this, (userCharacteristics) -> {
      populateFields(userCharacteristics);
    });

    return view;
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

  }


}
