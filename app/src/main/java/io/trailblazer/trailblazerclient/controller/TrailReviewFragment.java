///*
// * This work is Copyright, 2019, Isaac Lindland, Joel Bond, Khizar Saleem, Justin Dominguez
// * All rights reserved
// */
//
//package io.trailblazer.trailblazerclient.controller;
//
//
//import android.location.Location;
//import android.os.Bundle;
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import androidx.lifecycle.ViewModelProviders;
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.MapView;
//import com.google.android.gms.maps.MapsInitializer;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.model.CameraPosition;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.LatLngBounds;
//import com.google.android.gms.maps.model.MarkerOptions;
//import io.trailblazer.trailblazerclient.R;
//import io.trailblazer.trailblazerclient.model.Trail;
//import io.trailblazer.trailblazerclient.service.LocatorService;
//import io.trailblazer.trailblazerclient.viewmodel.TrailViewViewModel;
//import java.util.ArrayList;
//import java.util.List;
//
//
///**
// * A simple {@link Fragment} subclass.
// */
//public class TrailReviewFragment extends Fragment implements OnMapReadyCallback {
//
//
//  private View view;
//  private GoogleMap googleMap;
//  private MapView mapView;
//
//  @Override
//  public View onCreateView(LayoutInflater inflater, ViewGroup container,
//      Bundle savedInstanceState) {
//    view = inflater.inflate(R.layout.trail_review_fragment, container, false);
//
//
//    return view;
//  }
//
//  @Override
//  public void onViewCreated(View view, Bundle savedInstanceState) {
//    super.onViewCreated(view, savedInstanceState);
//    mapView = view.findViewById(R.id.trail_review);
//    if (mapView != null) {
//      mapView.onCreate(null);
//      mapView.onResume();
//      mapView.getMapAsync(this);
//    }
//  }
//
//  @Override
//  public void onMapReady(GoogleMap googleMap) {
//    MapsInitializer.initialize(getContext());
//    this.googleMap = googleMap;
//    googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
////    LocatorService.getInstance().getLocationList().observe(this, locations -> {
////
////      List<LatLng> points = locationsToLatLng(locations);
////      googleMap.addMarker(new MarkerOptions().position(new LatLng(cn.getLatitude(), cn.getLongitude())).title(cn.getDescription()));
////      bounds = new LatLngBounds.Builder().include(new LatLng(cn.getLatitude(), cn.getLongitude())).build();
////      googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(here));
////
////      googleMap.setOnMapLoadedCallback(() -> {
////      });
////    });
////    LocatorService.getInstance().requestCurrentLocation().addOnSuccessListener(location -> {
////      CameraPosition here = CameraPosition.builder()
////          .target(new LatLng(location.getLatitude(), location.getLongitude())).zoom(16).bearing(0)
////          .build();
////
////    });
//  }
//
//  private List<LatLng> locationsToLatLng(List<Location> locations) {
//    List<LatLng> p = new ArrayList<>();
//    for (Location location : locations) {
//      p.add(new LatLng(location.getLatitude(), location.getLongitude()));
//    }
//    return p;
//  }
//}
