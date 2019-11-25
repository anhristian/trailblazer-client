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
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import io.trailblazer.trailblazerclient.R;

public class MapsFragment extends Fragment implements OnMapReadyCallback {

  private GoogleMap googleMap;
  private MapView mapView;
  private View view;


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

    googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    googleMap.addMarker(new MarkerOptions().position(new LatLng( 35.085601, -106.649326 )).title("abq"));

    CameraPosition abq = CameraPosition.builder().target(new LatLng( 35.085601, -106.649326 )).zoom(16)
        .bearing(0).tilt(45).build();
    googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(abq));

    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-23.684, 133.903), 4));

    Polyline polyline1 = googleMap.addPolyline(new PolylineOptions()
        .clickable(true)
        .add(
            new LatLng(35.16430139231649, -106.46370012666549),
            new LatLng(35.16415681961822, -106.46402087050099),
            new LatLng(35.16407902961823, -106.46422204050096),
            new LatLng(35.16406830961823, -106.46424081050095),
            new LatLng(35.16391005961822, -106.46474238050092)));
    googleMap.moveCamera(
        CameraUpdateFactory.newLatLngZoom(new LatLng(35.16430139231649, -106.46370012666549), 17));

  }
}
