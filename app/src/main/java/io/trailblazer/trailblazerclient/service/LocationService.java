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
import android.os.HandlerThread;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import java.util.LinkedList;
import java.util.List;

public class LocationService extends LocationCallback implements LocationListener,
    ConnectionCallbacks,
    OnConnectionFailedListener {

  private static final int PERMISSIONS_REQUEST_CODE = 1000;
  private static final String TAG = "LocationService";
  private static Application applicationContext;
  private LocationManager locationManager;
  private List<Location> locations;
  private MutableLiveData<Location> currentLocation;
  private MutableLiveData<Location> updatedLocation;

  private HandlerThread mBackgroundThread;


  public LocationService() {
    locations = new LinkedList<>();
    locationManager = (LocationManager) applicationContext.getSystemService(
        Context.LOCATION_SERVICE);

    currentLocation = new MutableLiveData<>();
    updatedLocation = new MutableLiveData<>();
    mBackgroundThread = new HandlerThread("looperThread");

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
    startLocationUpdates();
  }

  public LiveData<Location> getCurrentLocation() {
    return currentLocation;
  }

  public void requestCurrentLocation() {
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

  private LocationRequest createLocationRequest() {
    LocationRequest locationRequest = new LocationRequest();
    locationRequest.setSmallestDisplacement(1f);
    locationRequest.setInterval(0);
    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    return locationRequest;
  }


  private void startLocationUpdates() {
    mBackgroundThread = new HandlerThread("location");
    mBackgroundThread.start();
    LocationServices.getFusedLocationProviderClient(applicationContext)
        .requestLocationUpdates(createLocationRequest(), this, mBackgroundThread.getLooper());
  }

  public void stopLocationUpdates() {

    LocationServices.getFusedLocationProviderClient(applicationContext).removeLocationUpdates(this);
    mBackgroundThread.quit();
    mBackgroundThread = null;

  }

  public LiveData<Location> getUpdatedLocation() {
    return updatedLocation;
  }

  @Override
  public void onLocationResult(LocationResult locationResult) {
    if (locationResult == null) {
      return;
    }
    for (Location location : locationResult.getLocations()) {

      updatedLocation.postValue(location);
      Log.d(TAG, "onLocationResult: " + location.toString());
      locations.add(location);
    }
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
