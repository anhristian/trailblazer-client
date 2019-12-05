/*
 * This work is Copyright, 2019, Isaac Lindland, Joel Bond, Khizar Saleem, Justin Dominguez
 * All rights reserved
 */

package io.trailblazer.trailblazerclient.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener;
import com.google.android.material.tabs.TabLayout.Tab;
import io.trailblazer.trailblazerclient.R;
import io.trailblazer.trailblazerclient.view.TrailAdapter;
import io.trailblazer.trailblazerclient.viewmodel.TrailViewViewModel;

/**
 * The type Trail viewer fragment.
 */
public class TrailViewerFragment extends Fragment {

  private RecyclerView recyclerView;
  private View view;
  private TrailViewViewModel trailViewViewModel;
  private TabItem myTrailsTab;
  private TabItem publicTrailsTab;
  private TrailAdapter trailAdapter;
  private TabLayout tabs;


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    trailViewViewModel = ViewModelProviders.of(this).get(TrailViewViewModel.class);
    view = inflater.inflate(R.layout.trail_view_fragment, container, false);
    initViews();
    initListeners();

    trailViewViewModel.refreshPublicTrails();

    return view;
  }

  private void initViews() {
    recyclerView = view.findViewById(R.id.trail_view);
    tabs = view.findViewById(R.id.tabs);
  }

  private void initListeners() {
    tabs.addOnTabSelectedListener(new BaseOnTabSelectedListener() {
      @Override
      public void onTabSelected(Tab tab) {
        if (tab.getPosition() == 0) {
          trailViewViewModel.refreshPublicTrails();
          getAllTrails();
        } else if (tab.getPosition() == 1) {
          trailViewViewModel.refreshMyTrails();
          getMyTrails();
        }

      }

      @Override
      public void onTabUnselected(Tab tab) {

      }

      @Override
      public void onTabReselected(Tab tab) {

      }
    });
    trailViewViewModel.getThrowable().observe(this, (throwable) -> {
      Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_LONG).show();
    });
  }


  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    LayoutAnimationController animation = AnimationUtils
        .loadLayoutAnimation(getContext(), R.anim.layout_animation_fall_down);
    recyclerView.setLayoutAnimation(animation);

    LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
    recyclerView.setLayoutManager(mLayoutManager);
    trailAdapter = new TrailAdapter(getContext(), (v, position, trail) -> {
      trailViewViewModel.getTrail(trail);
      trailViewViewModel.getSingleTrail().observe(this, (t) -> {
        Bundle bundle = new Bundle();
        bundle.putSerializable("trail", trail);
        Navigation.findNavController(view).navigate(R.id.map_nav, bundle);
      });
    });
    recyclerView.setAdapter(trailAdapter);
    getAllTrails();

  }

  private void getAllTrails() {
    trailViewViewModel.getPublicTrails().observe(this, (trails) -> {
      trailAdapter.setTrails(trails);
      recyclerView.getAdapter().notifyDataSetChanged();
      recyclerView.scheduleLayoutAnimation();
    });
  }


  private void getMyTrails() {
    trailViewViewModel.getMyTrails().observe(this, (trails) -> {
      trailAdapter.setTrails(trails);
      recyclerView.getAdapter().notifyDataSetChanged();
      recyclerView.scheduleLayoutAnimation();

    });
  }


}


