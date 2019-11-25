package io.trailblazer.trailblazerclient.service;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.trailblazer.trailblazerclient.model.Trail;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface NetworkService {

  @GET("trails/public")
  Observable<List<Trail>> getAllTrails();

  @GET("trails/")
  Observable<List<Trail>> getAllAuthenticated(@Header("Authorization") String token);

  @GET("trails/{id}")
  Single<Trail> getTrailById(@Header("Authorization") String token, @Path("id") long id);


}
