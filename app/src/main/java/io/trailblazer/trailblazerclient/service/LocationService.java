/*
 * This work is Copyright, 2019, Isaac Lindland, Joel Bond, Khizar Saleem, Justin Dominguez
 * All rights reserved
 */

package io.trailblazer.trailblazerclient.service;

import android.Manifest.permission;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import java.util.LinkedList;
import java.util.List;

public class LocationService extends AppCompatActivity implements LocationListener, ConnectionCallbacks,
    OnConnectionFailedListener {

  private static final int PERMISSIONS_REQUEST_CODE = 1000;
  private static Application applicationContext;
  private LocationServices locationServices;
  private LocationManager locationManager;
  private List<Location> locations;
  private MutableLiveData<Location> currentLocation;
  private FusedLocationProviderClient fusedLocationProviderClient;
  private boolean requestingLocationUpdates;
  private LocationCallback locationCallback;
  private LocationRequest locationRequest;


  public LocationService() {
    locations = new LinkedList<>();
    locationManager = (LocationManager) applicationContext.getSystemService(
        Context.LOCATION_SERVICE);
    currentLocation = new MutableLiveData<>();
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
    locationCallback = new LocationCallback() {
      @Override
      public void onLocationResult(LocationResult locationResult) {
        if (locationResult == null) {
          return;
        }
        for (Location location : locationResult.getLocations()) {
          // TODO Update UI with location data

        }
      }
    };
  }

  public static void setApplicationContext(Application applicationContext) {
    LocationService.applicationContext = applicationContext;
  }

  public static LocationService getInstance() {
    return InstanceHolder.INSTANCE;
  }

  public void start() {

    if (applicationContext.checkSelfPermission(permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED
        && applicationContext.checkSelfPermission(permission.ACCESS_COARSE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {
      return;
    }

//    LocationServices.getFusedLocationProviderClient(applicationContext)
//        .requestLocationUpdates().addOnSuccessListener()

  }

  public LiveData<Location> getCurrentLocation() {
    return currentLocation;
  }

  public void setCurrentLocation() {
    if (applicationContext.checkSelfPermission(permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED
        && applicationContext.checkSelfPermission(permission.ACCESS_COARSE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {
      return;
    }
    LocationServices.getFusedLocationProviderClient(applicationContext).getLastLocation()
        .addOnSuccessListener(location -> {
          currentLocation.postValue(location);
        });

  }

  private void createLocationRequest() {
    LocationRequest locationRequest = new LocationRequest();
    locationRequest.setInterval(10000);
    locationRequest.setFastestInterval(5000);
    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
  }

  @Override
  protected void onResume() {
    super.onResume();
    if (requestingLocationUpdates) {
      startLocationUpdates();
    }
  }

  private void startLocationUpdates() {
    fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper
        .getMainLooper());
  }

  @Override
  public void onLocationChanged(Location location) {

  }

  @Override
  public void onStatusChanged(String provider, int status, Bundle extras) {

  }

  @Override
  public void onProviderEnabled(String provider) {

  }

  @Override
  public void onProviderDisabled(String provider) {
    Toast.makeText(applicationContext, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
  }

  @Override
  public void onConnected(@Nullable Bundle bundle) {

  }

  @Override
  public void onConnectionSuspended(int i) {

  }

  @Override
  public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

  }


  private static class InstanceHolder {

    private static final LocationService INSTANCE = new LocationService();
  }


}
