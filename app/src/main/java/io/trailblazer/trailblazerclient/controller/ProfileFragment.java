package io.trailblazer.trailblazerclient.controller;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import io.trailblazer.trailblazerclient.R;


public class ProfileFragment extends Fragment {


  private View view;
  private Context context;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    view = inflater.inflate(R.layout.profile_fragment, container, false);
    context = container.getContext();
    return view;
  }

}
