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
//    DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
//    float screenWidth = metrics.widthPixels;
//    float itemWidth = getContext().getResources().getDimension(R.dimen.gallery_item_width);

//    int cols = (int) Math.floor(screenWidth / itemWidth);
//
//    GridLayoutManager manager = new GridLayoutManager(getContext(), cols);
//
//    recyclerView.setLayoutManager(manager);
//    viewModel = ViewModelProviders.of(this).get(GalleryViewModel.class);
//
//    viewModel.getImages().observe(this, images -> {
//      GalleryAdapter galleryAdapter = new GalleryAdapter(getContext(), images);
//      recyclerView.setAdapter(galleryAdapter);
//    });
    LayoutAnimationController animation = AnimationUtils
        .loadLayoutAnimation(context, R.anim.layout_animation_fall_down);
    recyclerView.setLayoutAnimation(animation);

    LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);
    recyclerView.setLayoutManager(mLayoutManager);
    trailViewViewModel.getPublicTrails().observe(this, (trails) -> {
      TrailAdapter trailAdapter = new TrailAdapter(context, trails);

      recyclerView.setAdapter(trailAdapter);
      recyclerView.getAdapter().notifyDataSetChanged();
      recyclerView.scheduleLayoutAnimation();
    });


  }


}


