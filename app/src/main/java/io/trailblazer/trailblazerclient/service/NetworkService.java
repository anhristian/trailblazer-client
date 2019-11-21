package io.trailblazer.trailblazerclient.service;

import io.reactivex.Observable;
import io.trailblazer.trailblazerclient.model.Trail;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface NetworkService {

  @GET("trails/public")
  Observable<List<Trail>> getAllTrails();



}
