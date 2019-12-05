/*
 * This work is Copyright, 2019, Isaac Lindland, Joel Bond, Khizar Saleem, Justin Dominguez
 * All rights reserved
 */

package io.trailblazer.trailblazerclient.viewmodel;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle.Event;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.trailblazer.trailblazerclient.R;
import io.trailblazer.trailblazerclient.model.Trail;
import io.trailblazer.trailblazerclient.service.GoogleSignInService;
import io.trailblazer.trailblazerclient.service.NetworkService;
import java.util.List;

public class TrailViewModel extends AndroidViewModel implements LifecycleObserver {

  private final MutableLiveData<Throwable> throwable;
  private final CompositeDisposable pending;
  private final MutableLiveData<GoogleSignInAccount> account;
  private final MutableLiveData<List<Trail>> publicTrails;
  private final MutableLiveData<Trail> singleTrail;
  private final MutableLiveData<List<Trail>> myTrails;
  private final MutableLiveData<Trail> postedTrail;

  public TrailViewModel(@NonNull Application application) {
    super(application);
    throwable = new MutableLiveData<>();
    pending = new CompositeDisposable();
    publicTrails = new MutableLiveData<>();
    myTrails = new MutableLiveData<>();
    account = new MutableLiveData<>();
    singleTrail = new MutableLiveData<>();
    postedTrail = new MutableLiveData<>();
  }


  public LiveData<List<Trail>> getPublicTrails() {
    return publicTrails;
  }

  public LiveData<Trail> getPostedTrail() {
    return postedTrail;
  }

  public void getTrail(Trail trail) {
    pending.add(
        NetworkService.getInstance().getTrailById(getAuthorizationHeader(), trail.getId())
            .subscribeOn(Schedulers.io())
            .subscribe(this.singleTrail::postValue, this.throwable::postValue)
    );
  }

  public void refreshMyTrails() {
    pending.add(
        NetworkService.getInstance().getMyTrails(getAuthorizationHeader())
            .subscribeOn(Schedulers.io())
            .subscribe(this.myTrails::postValue, this.throwable::postValue)
    );
  }

  public void refreshPublicTrails() {
    pending.add(
        NetworkService.getInstance().getAllTrails()
            .subscribeOn(Schedulers.io())
            .subscribe(this.publicTrails::postValue, this.throwable::postValue)
    );
  }

  public void postTrail(Trail trail) {
    pending.add(
        NetworkService.getInstance().postTrail(getAuthorizationHeader(), trail)
            .subscribeOn(Schedulers.io())
            .subscribe(this.postedTrail::postValue, this.throwable::postValue)
    );
  }


  public LiveData<Throwable> getThrowable() {
    return throwable;
  }

  public void setAccount(GoogleSignInAccount account) {
    this.account.setValue(account);
  }

  private String getAuthorizationHeader() {
    // FIXME finish adding token to viewmodel instead of querying signinservice
    String token = getApplication()
        .getString(R.string.oauth_header,
            GoogleSignInService.getInstance().getAccount().getValue().getIdToken());
    Log.d("OAuth2.0 token", token);
    return token;
  }


  @OnLifecycleEvent(Event.ON_STOP)
  private void clearPending() {
    pending.clear();
  }


  public LiveData<Trail> getSingleTrail() {
    return singleTrail;
  }

  public LiveData<List<Trail>> getMyTrails() {
    return myTrails;
  }

}
