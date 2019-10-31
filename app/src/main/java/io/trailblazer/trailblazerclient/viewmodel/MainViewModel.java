package io.trailblazer.trailblazerclient.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class MainViewModel  extends AndroidViewModel {

  private final MutableLiveData<GoogleSignInAccount> account =
      new MutableLiveData<>();
  private final MutableLiveData<Throwable> throwable =
      new MutableLiveData<>();


  public MainViewModel(@NonNull Application application) {
    super(application);
  }

  public LiveData<Throwable> getThrowable() {
    return throwable;
  }

  public void setAccount(GoogleSignInAccount account) {
    this.account.setValue(account);
  }


  }
