package io.trailblazer.trailblazerclient.controller;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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
import io.trailblazer.trailblazerclient.R;
import io.trailblazer.trailblazerclient.model.Trail;
import io.trailblazer.trailblazerclient.service.LocationService;
import io.trailblazer.trailblazerclient.viewmodel.TrailViewViewModel;
import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback {

  private static final String TAG = "mapfragment";
  private GoogleMap googleMap;
  private MapView mapView;
  private View view;
  private Context context;
  private TrailViewViewModel trailViewViewModel;
  private Trail trail;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

  }


  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    view = inflater.inflate(R.layout.maps_fragment, container, false);
    context = container.getContext();
    setRetainInstance(true);
    trailViewViewModel = ViewModelProviders.of(this)
        .get(TrailViewViewModel.class);
    Log.d(TAG, "onCreateView: ");
    return view;
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    mapView = view.findViewById(R.id.map);
    if (mapView != null) {
      mapView.onCreate(null);
      mapView.onResume();
      mapView.getMapAsync(this);
    }
  }

  @Override
  public void onMapReady(GoogleMap googleMap) {
    MapsInitializer.initialize(context);

    googleMap.setMyLocationEnabled(true);
    googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
    LocationService.getInstance().setCurrentLocation();
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
          graph(trail, googleMap);
        }
      });
    });
//
//    CameraPosition abq = CameraPosition.builder().target(new LatLng( 35.085601, -106.649326 )).zoom(16)
//        .bearing(0).tilt(45).build();
//    googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(abq));
//
//    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-23.684, 133.903), 4));
//
//    Polyline polyline1 = googleMap.addPolyline(new PolylineOptions()
//        .clickable(true)
//        .add(
//            new LatLng(35.16430139231649, -106.46370012666549),
//            new LatLng(35.16415681961822, -106.46402087050099),
//            new LatLng(35.16407902961823, -106.46422204050096),
//            new LatLng(35.16406830961823, -106.46424081050095),
//            new LatLng(35.16391005961822, -106.46474238050092)));
//    googleMap.moveCamera(
//        CameraUpdateFactory.newLatLngZoom(new LatLng(35.16430139231649, -106.46370012666549), 17));

  }

  private void graph(Trail trail, GoogleMap googleMap) {
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


  private void graphAllTrails(GoogleMap googleMap) {
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
