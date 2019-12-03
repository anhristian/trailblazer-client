/*
 * This work is Copyright, 2019, Isaac Lindland, Joel Bond, Khizar Saleem, Justin Dominguez
 * All rights reserved
 */

package io.trailblazer.trailblazerclient.controller;

import android.content.Context;
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
import io.trailblazer.trailblazerclient.R;
import io.trailblazer.trailblazerclient.view.TrailAdapter;
import io.trailblazer.trailblazerclient.viewmodel.TrailViewViewModel;

public class TrailViewerFragment extends Fragment {

  private RecyclerView recyclerView;
  private View view;
  private TrailViewViewModel trailViewViewModel;
  private Context context;


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    trailViewViewModel = ViewModelProviders.of(this).get(TrailViewViewModel.class);
    view = inflater.inflate(R.layout.trail_view_fragment, container, false);
    context = container.getContext();
    recyclerView = view.findViewById(R.id.trail_view);
    trailViewViewModel.refreshPublicTrails();
    trailViewViewModel.getThrowable().observe(this, (throwable) -> {
      Toast.makeText(context, throwable.getMessage(), Toast.LENGTH_LONG).show();
    });
    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    LayoutAnimationController animation = AnimationUtils
        .loadLayoutAnimation(context, R.anim.layout_animation_fall_down);
    recyclerView.setLayoutAnimation(animation);

    LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);
    recyclerView.setLayoutManager(mLayoutManager);
    TrailAdapter trailAdapter = new TrailAdapter(context, (v, position, trail) -> {
      trailViewViewModel.getTrail(trail);
      trailViewViewModel.getSingleTrail().observe(this, (t) -> {
        Bundle bundle = new Bundle();
        bundle.putSerializable("trail", trail);
        Navigation.findNavController(view).navigate(R.id.map_nav, bundle);
      });
    });
    recyclerView.setAdapter(trailAdapter);
    getAllTrails(trailAdapter);


  }

  private void getAllTrails(TrailAdapter trailAdapter) {
    trailViewViewModel.getPublicTrails().observe(this, (trails) -> {
      trailAdapter.setTrails(trails);
      recyclerView.getAdapter().notifyDataSetChanged();
      recyclerView.scheduleLayoutAnimation();
    });
  }


}


