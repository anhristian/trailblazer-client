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
import io.trailblazer.trailblazerclient.model.UserCharacteristics;
import io.trailblazer.trailblazerclient.viewmodel.UserViewModel;


public class ProfileFragment extends Fragment {


  private View view;
  private Context context;
  private UserViewModel userViewModel;
  private boolean editing = false;
  private ImageView editProfile;
  private EditText editUsername;
  private EditText editAge;
  private EditText editWeight;
  private EditText editHeight;
  private EditText editFirstName;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
    view = inflater.inflate(R.layout.profile_fragment, container, false);
    context = container.getContext();
    initViews();
    userViewModel.setUser();
    userViewModel.getUser().observe(this, this::setFields);



    return view;
  }

  private void setFields(UserCharacteristics user) {
    if (user.getFirstName() != null) {
      editFirstName.setText(user.getFirstName());
    }
    if (user.getHeightInches() != null) {
      editHeight.setText(user.getHeightInches().toString());
    }
    if (user.getUser() != null && user.getUser().getUsername() != null) {
      editUsername.setText(user.getUser().getUsername());
    }
    if (user.getAge() != null) {
      editAge.setText(user.getAge().toString());
    }
    if (user.getWeightLbs() != null) {
      editWeight.setText(user.getWeightLbs().toString());
    }


  }

  private void initViews() {
    editProfile = view.findViewById(R.id.edit);
    editUsername = view.findViewById(R.id.edit_username);
    editAge = view.findViewById(R.id.edit_age);
    editWeight = view.findViewById(R.id.edit_weight);
    editHeight = view.findViewById(R.id.edit_height);
    editFirstName = view.findViewById(R.id.edit_first_name);

    editUsername.setEnabled(editing);
    editFirstName.setEnabled(editing);
    editAge.setEnabled(editing);
    editWeight.setEnabled(editing);
    editHeight.setEnabled(editing);

  }



}
