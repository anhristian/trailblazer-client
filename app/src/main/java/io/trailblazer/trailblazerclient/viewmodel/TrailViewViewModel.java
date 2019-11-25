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
import io.trailblazer.trailblazerclient.service.NetworkService;
import java.util.List;

public class TrailViewViewModel extends AndroidViewModel implements LifecycleObserver {

  private final MutableLiveData<Throwable> throwable;
  private final CompositeDisposable pending;
  private final MutableLiveData<GoogleSignInAccount> account;
  private final MutableLiveData<List<Trail>> publicTrails;

  public TrailViewViewModel(@NonNull Application application) {
    super(application);
    throwable = new MutableLiveData<>();
    pending = new CompositeDisposable();
    publicTrails = new MutableLiveData<>();
    account = new MutableLiveData<>();

  }


  public LiveData<List<Trail>> getPublicTrails() {
    return publicTrails;
  }

  public void refreshPublicTrails() {
    GoogleSignInAccount account = this.account.getValue();
    String token = getAuthorizationHeader(account);
    pending.add(
        NetworkService.getInstance().getAllTrails()
            .subscribeOn(Schedulers.io())
            .subscribe(this.publicTrails::postValue, this.throwable::postValue)
    );
  }

//  public void populateRecyclerView(){
//    GoogleSignInAccount account = this.account.getValue();
//    if (account != null) {
//      String token = getAuthorizationHeader(account);
//      pending.add(
//          NetworkService.getInstance().getAllAuthenticated(token)
//              .subscribeOn(Schedulers.io())
//              .subscribe((p) account), this.throwable::postValue)
//);
//    }
//  }


  private String getAuthorizationHeader(GoogleSignInAccount account) {
    String token = getApplication().getString(R.string.oauth_header, account.getIdToken());
    Log.d("OAuth2.0 token", token); // FIXME Remove before shipping.
    return token;
  }


  @OnLifecycleEvent(Event.ON_STOP)
  private void clearPending() {
    pending.clear();
  }


}
