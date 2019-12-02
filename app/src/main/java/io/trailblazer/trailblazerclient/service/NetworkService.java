package io.trailblazer.trailblazerclient.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.android.data.Geometry;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.trailblazer.trailblazerclient.BuildConfig;
import io.trailblazer.trailblazerclient.model.Trail;
import io.trailblazer.trailblazerclient.model.User;
import java.util.List;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NetworkService {

  static NetworkService getInstance() {
    return NetworkService.InstanceHolder.INSTANCE;
  }

  @GET("trails/public")
  Observable<List<Trail>> getAllTrails();

  @GET("trails/mytrails")
  Observable<List<Trail>> getMyTrails(@Header("Authorization") String token);

  @GET("trails/search")
  Observable<List<Trail>> getTrailsByName(@Header("Authorization") String token,
      @Query("name") String name);

  @GET("trails/")
  Observable<List<Trail>> getAllAuthenticated(@Header("Authorization") String token);

  @GET("trails/{id}")
  Single<Trail> getTrailById(@Header("Authorization") String token, @Path("id") long id);

  @GET("{id}/geometry")
  Observable<Geometry> getGeometry(@Header("Authorization") String token, @Path("id") long id);

  @GET("user")
  Single<User> getUser(@Header("Authorization") String token);

  @GET("user/stats")
  Single<User> getUserStats(@Header("Authorization") String token);

  @PUT("user")
  Single<User> updateUser(@Header("Authorization") String token);

  @POST("trails")
  Single<Trail> postTrail(@Header("Authorization") String token, @Body Trail trail);



  class InstanceHolder {


    private static final NetworkService INSTANCE;

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
      INSTANCE = retrofit.create(NetworkService.class);
    }

    static NetworkService getInstance() {
      return InstanceHolder.INSTANCE;
    }

  }
}
