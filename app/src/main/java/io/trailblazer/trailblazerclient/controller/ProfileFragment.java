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
    editProfile = view.findViewById(R.id.edit);
    editUsername = view.findViewById(R.id.edit_username);
    editFirstName = view.findViewById(R.id.edit_first_name);
    editLastName = view.findViewById(R.id.edit_last_name);
    editAge = view.findViewById(R.id.edit_age);
    editWeight = view.findViewById(R.id.edit_weight);
    editHeight = view.findViewById(R.id.edit_height);
    editUsername.setEnabled(false);
    editFirstName.setEnabled(false);
    editLastName.setEnabled(false);
    editAge.setEnabled(false);
    editWeight.setEnabled(false);
    editHeight.setEnabled(false);



//    user = (User) getArguments().getSerializable("user");
//    if (user == null) {
//      user = new User();
//    }
    if (savedInstanceState == null) {
//      populateFields();
    }

    return view;
  }

//  private void populateFields() {
//    if (user.getUsername() != null) {
//      editName.setText(user.getUsername());
//    }
//  }

}
