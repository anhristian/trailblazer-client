package io.trailblazer.trailblazerclient.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import io.trailblazer.trailblazerclient.R;

public class MapsFragment extends Fragment implements OnMapReadyCallback {

  GoogleMap googleMap;
  MapView mapView;
  View view;

  public MapsFragment() {

  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }


  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    view = inflater.inflate(R.layout.maps_fragment, container, false);
    return view;
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    mapView = (MapView) view.findViewById(R.id.map);
    if (mapView != null) {
      mapView.onCreate(null);
      mapView.onResume();
      mapView.getMapAsync(this);

    }
  }

  @Override
  public void onMapReady(GoogleMap googleMap) {

    MapsInitializer.initialize(getContext());

    googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    googleMap.addMarker(new MarkerOptions().position(new LatLng( 35.085601, -106.649326 )).title("abq"));

    CameraPosition abq = CameraPosition.builder().target(new LatLng( 35.085601, -106.649326 )).zoom(16)
        .bearing(0).tilt(45).build();
    googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(abq));
  }
}
