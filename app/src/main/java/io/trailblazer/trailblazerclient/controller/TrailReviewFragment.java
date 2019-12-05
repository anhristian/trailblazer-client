/*
 * This work is Copyright, 2019, Isaac Lindland, Joel Bond, Khizar Saleem, Justin Dominguez
 * All rights reserved
 */

package io.trailblazer.trailblazerclient.controller;


import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds.Builder;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.textfield.TextInputEditText;
import io.trailblazer.trailblazerclient.R;
import io.trailblazer.trailblazerclient.model.Geometry;
import io.trailblazer.trailblazerclient.model.Trail;
import io.trailblazer.trailblazerclient.service.LocatorService;
import io.trailblazer.trailblazerclient.viewmodel.TrailViewModel;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TrailReviewFragment extends DialogFragment implements OnMapReadyCallback {


  private static final String TAG = "trailreviewfragment";
  private View view;
  private GoogleMap googleMap;
  private MapView mapView;
  private TextInputEditText trailNameText;
  private TrailViewModel trailViewModel;
  private List<LatLng> points;


  private TrailReviewFragment() {

  }


  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mapView = view.findViewById(R.id.trail_review);
    trailViewModel = ViewModelProviders.of(getParentFragment()).get(TrailViewModel.class);

    if (mapView != null) {
      mapView.onCreate(null);
      mapView.onResume();
      mapView.getMapAsync(this);
    }
    trailNameText = view.findViewById(R.id.trail_name_save);
  }

  public static TrailReviewFragment newInstance() {
    TrailReviewFragment fragment = new TrailReviewFragment();
    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return view;
  }

  private List<LatLng> locationsToLatLng(List<Location> locations) {
    List<LatLng> p = new ArrayList<>();
    for (Location location : locations) {
      p.add(new LatLng(location.getLatitude(), location.getLongitude()));
    }
    return p;
  }

  @Override
  public void onMapReady(GoogleMap googleMap) {
    MapsInitializer.initialize(getContext());
    this.googleMap = googleMap;

    googleMap.setOnMapLoadedCallback(() -> {
      LocatorService.getInstance().getLocationList().observe(this, locations -> {
        if (locations.size() != 0) {
          points = locationsToLatLng(locations);
          googleMap.addMarker(new MarkerOptions().position(points.get(0)));
          Builder bounds = new Builder();
          for (LatLng point : points) {
            bounds.include(point);
          }
          PolylineOptions polylineOptions = new PolylineOptions()
              .color(Color.BLUE)
              .jointType(JointType.ROUND);

          googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 0));

          googleMap.setOnMapLoadedCallback(() -> {
            Polyline polyline = googleMap.addPolyline(polylineOptions);
            polyline.setPoints(points);
          });
        }
      });
    });

  }

  @NonNull
  @Override
  public AlertDialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    view = getActivity().getLayoutInflater().inflate(R.layout.trail_review_fragment, null);
    return new AlertDialog.Builder(getContext())
//        .setTitle(getString(R.string.new_trail))
        .setView(view)
        .setNegativeButton(getString(R.string.cancel), (dialog, button) -> cancel())
        .setPositiveButton(getString(R.string.ok), (dialog, button) -> accept())
        .create();
  }

  private void cancel() {
    // TODO handle cancelling fragment
  }

  private void accept() {
    Trail trail = new Trail();
    trail.setName(trailNameText.getText().toString());
    Geometry geometry = new Geometry();
    geometry.setType("LineString");
    double[][] p = new double[points.size()][];
    for (int i = 0; i < points.size(); i++) {
      p[i] = new double[]{points.get(i).longitude, points.get(i).latitude};
    }
    geometry.setCoordinates(p);
    Log.d(TAG, "accept: " + trail);
    trailViewModel.postTrail(trail);
  }


}
