/*
 * This work is Copyright, 2019, Isaac Lindland, Joel Bond, Khizar Saleem, Justin Dominguez
 * All rights reserved
 */

package io.trailblazer.trailblazerclient.controller;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;
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
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import io.trailblazer.trailblazerclient.R;
import io.trailblazer.trailblazerclient.model.Trail;
import io.trailblazer.trailblazerclient.service.LocatorService;
import io.trailblazer.trailblazerclient.viewmodel.TrailViewModel;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * The type Map fragment.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {


  private static final String TAG = "mapfragment";
  private static final int START_RAD = 2;
  private static final int LINE_WIDTH = 8;


  private GoogleMap googleMap;
  private MapView mapView;
  private View view;
  private TrailViewModel trailViewModel;
  private Trail trail;
  private PolylineOptions polylineOptions;
  private Polyline currentMapping;
  private List<LatLng> points;
  private ImageButton startStopButton;
  private boolean recording;


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
    points = new LinkedList<>();

    return view;
  }

  private void initListeners() {
    startStopButton.setOnClickListener((v) -> {
      if (!recording) {
        LocatorService.getInstance().startLocationUpdates();
        recordNewTrail();
        startStopButton.setBackgroundResource(R.drawable.stop_recording_shape);
        recording = true;

      } else {
        recording = false;
        googleMap.clear();
        LocatorService.getInstance().stopLocationUpdates();
        startStopButton.setBackgroundResource(R.drawable.start_recording_shape);
        TrailReviewFragment trailReviewFragment = TrailReviewFragment.newInstance();
        trailReviewFragment.show(getFragmentManager(), "Trail Review");
      }

    });

    LocatorService.getInstance().getUpdatedLocation().observe(this, location -> {
      if (location != null) {
        newPoint(location);
      }
    });


  }


  private void initViews() {
    setRetainInstance(true);
    startStopButton = view.findViewById(R.id.record);
    startStopButton.setBackgroundResource(R.drawable.start_recording_shape);
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    trailViewModel = ViewModelProviders.of(this).get(TrailViewModel.class);
    trailViewModel.getThrowable().observe(this, throwable -> {
      Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
    });
    mapView = view.findViewById(R.id.map);
    if (mapView != null) {
      mapView.onCreate(null);
      mapView.onResume();
      mapView.getMapAsync(this);
    }
  }

  private void recordNewTrail() {
    googleMap.clear();
    points.clear();
    polylineOptions = new PolylineOptions()
        .color(Color.BLUE)
        .jointType(JointType.ROUND);

    //    polylineOptions.width(LINE_WIDTH);

    LocatorService.getInstance().requestCurrentLocation().addOnSuccessListener(location -> {
      CircleOptions startPoint = new CircleOptions();
      startPoint.fillColor(0x5f0000ff)
          .center(locationToLatLng(location))
          .radius(START_RAD)
          .strokeColor(0x9f0000f9);
      googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(locationToLatLng(location), 20));
      googleMap.addCircle(startPoint);
    });

    currentMapping = googleMap.addPolyline(polylineOptions);
  }

  private void newPoint(Location location) {
    LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
    points.add(loc);
    currentMapping.setPoints(points);
    CameraUpdate curr = CameraUpdateFactory.newLatLng(loc);
    googleMap.animateCamera(curr);
  }


  @Override
  public void onMapReady(GoogleMap googleMap) {
    MapsInitializer.initialize(getContext());
    this.googleMap = googleMap;
    googleMap.setMyLocationEnabled(true);
    googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    LocatorService.getInstance().requestCurrentLocation().addOnSuccessListener(location -> {
      CameraPosition here = CameraPosition.builder()
          .target(new LatLng(location.getLatitude(), location.getLongitude())).zoom(16).bearing(0)
          .build();
      googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(here));

      googleMap.setOnMapLoadedCallback(() -> {
        if (getArguments() != null && getArguments().containsKey("trail")) {
          trail = ((Trail) getArguments().getSerializable("trail"));
          graphSingleTrail(trail);
        }
      });
    });
  }

  private void graphSingleTrail(Trail trail) {
    if (trail != null) {
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
      CameraUpdate loc = CameraUpdateFactory.newLatLngZoom(points.get(0), 17);

      googleMap.animateCamera(loc, 6000, null);
    }


  }

  private void graphAllTrails() {
    trailViewModel.refreshPublicTrails();
    trailViewModel.getPublicTrails().observe(this, (trails) -> {
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

  private LatLng locationToLatLng(Location location) {
    return new LatLng(location.getLatitude(), location.getLongitude());
  }

  @Override
  public void onPause() {
    super.onPause();
    if (recording) {
      LocatorService.getInstance().stopLocationUpdates();
      recording = false;
    }
  }
}
