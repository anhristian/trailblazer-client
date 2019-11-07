package io.trailblazer.trailblazerclient.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.reactivex.Observable;
import io.trailblazer.trailblazerclient.BuildConfig;
import io.trailblazer.trailblazerclient.model.Trail;
import java.util.List;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface TrailBlazerService {

  @GET("trails/search")
  Observable<List<Trail>> getTrailsByName(@Header("Authorization") String token, @Query("name") String name);


  static TrailBlazerService getInstance() {
    return InstanceHolder.INSTANCE;
  }

  class InstanceHolder {

    private static final TrailBlazerService INSTANCE;
    static {
      // TODO Investigate logging interceptor issues.
      Gson gson = new GsonBuilder()
          .excludeFieldsWithoutExposeAnnotation()
          .create();
      Retrofit retrofit = new Retrofit.Builder()
          .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
          .addConverterFactory(GsonConverterFactory.create(gson))
          .baseUrl(BuildConfig.BASE_URL)
          .build();
      INSTANCE = retrofit.create(TrailBlazerService.class);
    }

  }

}
