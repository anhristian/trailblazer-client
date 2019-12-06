/*
 * This work is Copyright, 2019, Isaac Lindland, Joel Bond, Khizar Saleem, Justin Dominguez
 * All rights reserved
 */

package io.trailblazer.trailblazerclient.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
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
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener;
import com.google.android.material.tabs.TabLayout.Tab;
import io.trailblazer.trailblazerclient.R;
import io.trailblazer.trailblazerclient.model.Trail;
import io.trailblazer.trailblazerclient.view.TrailAdapter;
import io.trailblazer.trailblazerclient.view.TrailAdapter.OnClickListener;
import io.trailblazer.trailblazerclient.view.TrailAdapter.OnContextClickListener;
import io.trailblazer.trailblazerclient.viewmodel.TrailViewModel;

/**
 * The type Trail viewer fragment.
 */
public class TrailViewerFragment extends Fragment implements OnContextClickListener,
    OnClickListener {

  private RecyclerView recyclerView;
  private View view;
  private TrailViewModel trailViewModel;
  private TrailAdapter trailAdapter;
  private TabLayout tabs;


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    trailViewModel = ViewModelProviders.of(this).get(TrailViewModel.class);
    view = inflater.inflate(R.layout.trail_view_fragment, container, false);
    initViews();
    initListeners();

    trailViewModel.refreshPublicTrails();

    return view;
  }

  private void initViews() {
    recyclerView = view.findViewById(R.id.trail_view);
    tabs = view.findViewById(R.id.tabs);
  }

  private void initListeners() {
    tabs.addOnTabSelectedListener(new OnTabSelectedListener() {
      @Override
      public void onTabSelected(Tab tab) {
        if (tab.getPosition() == 0) {
          trailViewModel.refreshPublicTrails();
          getAllTrails();
        } else if (tab.getPosition() == 1) {
          trailViewModel.refreshMyTrails();
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
    trailViewModel.getThrowable().observe(this, (throwable) -> {
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
    trailAdapter = new TrailAdapter(getContext(), this, this);
    recyclerView.setAdapter(trailAdapter);
    getAllTrails();

  }

  private void getAllTrails() {
    trailViewModel.getPublicTrails().observe(this, (trails) -> {
      trailAdapter.setTrails(trails);
      recyclerView.getAdapter().notifyDataSetChanged();
      recyclerView.scheduleLayoutAnimation();
    });
  }


  private void getMyTrails() {
    trailViewModel.getMyTrails().observe(this, (trails) -> {
      trailAdapter.setTrails(trails);
      recyclerView.getAdapter().notifyDataSetChanged();
      recyclerView.scheduleLayoutAnimation();
    });
  }


  @Override
  public void onClick(View view, int position, Trail trail) {
    trailViewModel.getTrail(trail);
    trailViewModel.getSingleTrail().observe(this, (t) -> {
      Bundle bundle = new Bundle();
      bundle.putSerializable("trail", trail);
      Navigation.findNavController(view).navigate(R.id.map_nav, bundle);
    });
  }

  @Override
  public void onLongClick(Menu menu, int position, Trail trail) {
    getActivity().getMenuInflater().inflate(R.menu.trail_context, menu);
    menu.findItem(R.id.delete_trail).setOnMenuItemClickListener(
        (item) -> deleteTrail(trail));
  }

  private boolean deleteTrail(Trail trail) {
    trailViewModel.deleteTrail(trail);
    return true;
  }
}


