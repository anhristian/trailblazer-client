package io.trailblazer.trailblazerclient.service;

import android.Manifest.permission;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.android.gms.location.LocationServices;
import java.util.LinkedList;
import java.util.List;

public class LocationService implements LocationListener {

  private static final int PERMISSIONS_REQUEST_CODE = 1000;
  private static Application applicationContext;
  private LocationServices locationServices;
  private LocationManager locationManager;
  private List<Location> locations;
  private MutableLiveData<Location> currentLocation;

  public LocationService() {
    locations = new LinkedList<>();
    locationManager = (LocationManager) applicationContext.getSystemService(
        Context.LOCATION_SERVICE);
    currentLocation = new MutableLiveData<>();
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
//
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

  private static class InstanceHolder {

    private static final LocationService INSTANCE = new LocationService();
  }


}
