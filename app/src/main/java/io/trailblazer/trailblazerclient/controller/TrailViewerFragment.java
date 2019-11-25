package io.trailblazer.trailblazerclient.controller;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
  private TrailViewViewModel viewModel;
  private Context context;

  public TrailViewerFragment() {

  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    viewModel = ViewModelProviders.of(this).get(TrailViewViewModel.class);
    view = inflater.inflate(R.layout.trail_view_fragment, container, false);
    context = container.getContext();
    recyclerView = view.findViewById(R.id.trail_view);
    viewModel.refreshPublicTrails();
    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//    super.onViewCreated(view, savedInstanceState);
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
    LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);
    recyclerView.setLayoutManager(mLayoutManager);
    viewModel.getPublicTrails().observe(this, (trails) -> {
      TrailAdapter trailAdapter = new TrailAdapter(context, trails);
      recyclerView.setAdapter(trailAdapter);
    });


  }


}


