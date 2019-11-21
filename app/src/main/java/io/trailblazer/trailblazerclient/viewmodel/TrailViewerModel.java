package io.trailblazer.trailblazerclient.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.trailblazer.trailblazerclient.model.Trail;
import java.util.LinkedList;
import java.util.List;

public class TrailViewerModel extends AndroidViewModel {

  private MutableLiveData<List<Trail>> trails = new MutableLiveData<>();

  public TrailViewerModel(@NonNull Application application) {
    super(application);

  }
//public LiveData<List<>> getTrails()
}
