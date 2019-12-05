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
import androidx.fragment.app.Fragment;
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
import io.trailblazer.trailblazerclient.R;
import io.trailblazer.trailblazerclient.service.LocatorService;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TrailReviewFragment extends Fragment implements OnMapReadyCallback {


  private View view;
  private GoogleMap googleMap;
  private MapView mapView;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    view = inflater.inflate(R.layout.trail_review_fragment, container, false);

    return view;
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mapView = view.findViewById(R.id.trail_review);
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
    LocatorService.getInstance().getLocationList().observe(this, locations -> {
      if (locations.size() != 0) {
        List<LatLng> points = locationsToLatLng(locations);
        googleMap.addMarker(new MarkerOptions().position(points.get(0)));
        Builder bounds = new Builder();
        for (LatLng point : points) {
          bounds.include(point);
        }
        PolylineOptions polylineOptions = new PolylineOptions()
            .color(Color.BLUE)
            .jointType(JointType.ROUND);

        googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 15));

        googleMap.setOnMapLoadedCallback(() -> {
          Polyline polyline = googleMap.addPolyline(polylineOptions);
          polyline.setPoints(points);
        });
      }
    });
  }

  private List<LatLng> locationsToLatLng(List<Location> locations) {
    List<LatLng> p = new ArrayList<>();
    for (Location location : locations) {
      p.add(new LatLng(location.getLatitude(), location.getLongitude()));
    }
    return p;
  }
}
