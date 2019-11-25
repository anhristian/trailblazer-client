package io.trailblazer.trailblazerclient.controller;

import static java.security.AccessController.getContext;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.trailblazer.trailblazerclient.R;
import io.trailblazer.trailblazerclient.model.Trail;
import io.trailblazer.trailblazerclient.viewmodel.TrailViewerModel;

public class TrailViewerFragment {

  private RecyclerView recyclerView;
  private View view;
  private TrailViewerModel viewModel;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    view = inflater.inflate(R.layout.fragment_gallery, container, false);
    recyclerView = view.findViewById(R.id.gallery);

    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//    super.onViewCreated(view, savedInstanceState);
//    DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
//    float screenWidth = metrics.widthPixels;
//    float itemWidth = getContext().getResources().getDimension(R.dimen.gallery_item_width);

    int cols = (int) Math.floor(screenWidth / itemWidth);

    GridLayoutManager manager = new GridLayoutManager(getContext(), cols);

    recyclerView.setLayoutManager(manager);
    viewModel = ViewModelProviders.of(this).get(GalleryViewModel.class);

    viewModel.getImages().observe(this, images -> {
      GalleryAdapter galleryAdapter = new GalleryAdapter(getContext(), images);
      recyclerView.setAdapter(galleryAdapter);
    });


  }


}


