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
import io.trailblazer.trailblazerclient.model.User;
import io.trailblazer.trailblazerclient.service.GoogleSignInService;
import io.trailblazer.trailblazerclient.service.NetworkService;

public class UserViewModel extends AndroidViewModel implements LifecycleObserver {

  private final MutableLiveData<Throwable> throwable;
  private final CompositeDisposable pending;
  private final MutableLiveData<GoogleSignInAccount> account;
  private final MutableLiveData<User> singleUser;


  public UserViewModel(@NonNull Application application) {
    super(application);
    throwable = new MutableLiveData<>();
    pending = new CompositeDisposable();
    account = new MutableLiveData<>();
    singleUser = new MutableLiveData<>();
  }

  public void setUser() {
    pending.add(
        NetworkService.getInstance().getUser(getAuthorizationHeader())
        .subscribeOn(Schedulers.io())
        .subscribe(this.singleUser::postValue, this.throwable::postValue)
    );
  }

  public void setAccount(GoogleSignInAccount account) {
    this.account.setValue(account);
  }


  public MutableLiveData<Throwable> getThrowable() {
    return throwable;
  }

  public MutableLiveData<GoogleSignInAccount> getAccount() {
    return account;
  }

  public LiveData<User> getSingleUser() {
    return singleUser;
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



}
