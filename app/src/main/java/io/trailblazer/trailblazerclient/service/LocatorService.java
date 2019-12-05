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
import com.google.android.gms.tasks.Task;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LocatorService extends LocationCallback implements LocationListener,
    ConnectionCallbacks,
    OnConnectionFailedListener {

  private static final int PERMISSIONS_REQUEST_CODE = 1000;
  private static final String TAG = "LocationService";
  private static Application applicationContext;
  private LocationManager locationManager;
  private List<Location> locations;
  private MutableLiveData<Location> updatedLocation;
  private MutableLiveData<List<Location>> locationList;
  private HandlerThread mBackgroundThread;


  public LocatorService() {
    locations = new LinkedList<>();
    locationManager = (LocationManager) applicationContext.getSystemService(
        Context.LOCATION_SERVICE);

    locationList = new MutableLiveData<>(new ArrayList<>());
    updatedLocation = new MutableLiveData<>();
    mBackgroundThread = new HandlerThread("looperThread");

  }

  public static void setApplicationContext(Application applicationContext) {
    LocatorService.applicationContext = applicationContext;
  }

  public static LocatorService getInstance() {
    return InstanceHolder.INSTANCE;
  }

  public LiveData<List<Location>> getLocationList() {
    return locationList;
  }

  public Task<Location> requestCurrentLocation() {
    if (applicationContext.checkSelfPermission(permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED
        && applicationContext.checkSelfPermission(permission.ACCESS_COARSE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {
      return null;
    }
    return LocationServices.getFusedLocationProviderClient(applicationContext).getLastLocation();
  }

  private LocationRequest createLocationRequest() {
    LocationRequest locationRequest = new LocationRequest();
    locationRequest.setSmallestDisplacement(1f);
    locationRequest.setInterval(0);
    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    return locationRequest;
  }

  public LiveData<Location> getUpdatedLocation() {
    return updatedLocation;
  }

  public void startLocationUpdates() {
    if (applicationContext.checkSelfPermission(permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED
        && applicationContext.checkSelfPermission(permission.ACCESS_COARSE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {
      return;
    }

    mBackgroundThread = new HandlerThread("location");
    mBackgroundThread.start();
    LocationServices.getFusedLocationProviderClient(applicationContext)
        .requestLocationUpdates(createLocationRequest(), this, mBackgroundThread.getLooper());
  }

  public void stopLocationUpdates() {

    LocationServices.getFusedLocationProviderClient(applicationContext).removeLocationUpdates(this);
    locationList.getValue().clear();
    mBackgroundThread.quit();
    mBackgroundThread = null;

  }


  @Override
  public void onLocationResult(LocationResult locationResult) {
    if (locationResult == null) {
      return;
    }
    for (Location location : locationResult.getLocations()) {
      locationList.getValue().add(location);
      locationList.postValue(locationList.getValue());
      updatedLocation.postValue(location);
    }
  }

  public LiveData<List<Location>> getUpdatedLocations() {
    return locationList;
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

    private static final LocatorService INSTANCE = new LocatorService();
  }


}
