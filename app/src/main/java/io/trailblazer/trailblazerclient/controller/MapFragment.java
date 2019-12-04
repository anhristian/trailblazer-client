/*
 * This work is Copyright, 2019, Isaac Lindland, Joel Bond, Khizar Saleem, Justin Dominguez
 * All rights reserved
 */

package io.trailblazer.trailblazerclient.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import io.trailblazer.trailblazerclient.R;
import io.trailblazer.trailblazerclient.model.Trail;
import io.trailblazer.trailblazerclient.service.LocationService;
import io.trailblazer.trailblazerclient.viewmodel.TrailViewViewModel;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Map fragment.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {

  private static final String TAG = "mapfragment";
  private GoogleMap googleMap;
  private MapView mapView;
  private View view;
  private TrailViewViewModel trailViewViewModel;
  private Trail trail;
  private FloatingActionButton startStopButton;
  private boolean mapping;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

  }


  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    view = inflater.inflate(R.layout.maps_fragment, container, false);
    initViews();
    initListeners();
    return view;
  }

  private void initListeners() {
    startStopButton.setOnClickListener((v) -> {
      mapping = true;
      LocationService.getInstance().start();
    });
  }

  private void initViews() {
    setRetainInstance(true);
    startStopButton = view.findViewById(R.id.record);
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    trailViewViewModel = ViewModelProviders.of(this).get(TrailViewViewModel.class);
    mapView = view.findViewById(R.id.map);
    if (mapView != null) {
      mapView.onCreate(null);
      mapView.onResume();
      mapView.getMapAsync(this);
    }
  }

  @Override
  public void onMapReady(GoogleMap googleMap) {
    MapsInitializer.initialize(getContext());
    this.googleMap = googleMap;
    googleMap.setMyLocationEnabled(true);
    googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    LocationService.getInstance().requestCurrentLocation();
    LocationService.getInstance().getCurrentLocation().observe(this, (location) -> {
      CameraPosition here = CameraPosition.builder()
          .target(new LatLng(location.getLatitude(), location.getLongitude())).zoom(16).bearing(0)
          .build();
      googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(here));

      googleMap.setOnMapLoadedCallback(() -> {

        if (getArguments() != null && getArguments().containsKey("trail")) {
          trail = ((Trail) getArguments().getSerializable("trail"));
        }

        if (trail != null) {
          graph(trail);
        }
      });
    });

  }

  private void graph(Trail trail) {
    googleMap.clear();
    googleMap.addMarker(
        new MarkerOptions().position(new LatLng(trail.getGeometry().getCoordinates()[0][1],
            trail.getGeometry().getCoordinates()[0][0])).title(trail.getName()));
    PolylineOptions polyline = new PolylineOptions();
    List<LatLng> points = new ArrayList<>();
    for (double[] coordinate : trail.getGeometry().getCoordinates()) {
      LatLng latLng = new LatLng(coordinate[1], coordinate[0]);
      points.add(latLng);
      polyline.add(latLng);
    }
    polyline.color(0xffff0000);
    googleMap.addPolyline(polyline);
    CameraUpdate loc = CameraUpdateFactory.newLatLngZoom(points.get(0), 16);

    googleMap.animateCamera(loc, 6000, null);

  }

  private void graphAllTrails() {
    trailViewViewModel.refreshPublicTrails();
    trailViewViewModel.getPublicTrails().observe(this, (trails) -> {
      googleMap.clear();
      for (Trail trail : trails) {
        PolylineOptions polyline = new PolylineOptions();
        for (double[] coordinate : trail.getGeometry().getCoordinates()) {
          polyline.add(new LatLng(coordinate[1], coordinate[0]));
        }
        polyline.color(0xffff0000);
        googleMap.addPolyline(polyline);
      }


    });
  }


}
