/*
 * This work is Copyright, 2019, Isaac Lindland, Joel Bond, Khizar Saleem, Justin Dominguez
 * All rights reserved
 */

package io.trailblazer.trailblazerclient.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.maps.android.data.Geometry;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.trailblazer.trailblazerclient.BuildConfig;
import io.trailblazer.trailblazerclient.model.Trail;
import io.trailblazer.trailblazerclient.model.User;
import io.trailblazer.trailblazerclient.model.UserCharacteristics;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
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
  Single<UserCharacteristics> getUser(@Header("Authorization") String token);

  @PUT("user")
  Single<UserCharacteristics> updateUser(@Header("Authorization") String token,
      @Body UserCharacteristics userCharacteristics);

  @PUT("user/username")
  Single<User> updateUsername(@Header("Authorization") String token,
      @Body UserCharacteristics userCharacteristics);

  @POST("trails")
  Single<Trail> postTrail(@Header("Authorization") String token, @Body Trail trail);



  class InstanceHolder {


    private static final NetworkService INSTANCE;

    static {
      HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
      interceptor.setLevel(Level.BODY);
      OkHttpClient client = new OkHttpClient.Builder()
          .addInterceptor(interceptor)
          .build();

      Gson gson = new GsonBuilder()
          .excludeFieldsWithoutExposeAnnotation()
          .registerTypeAdapter(Date.class, new DateDeserializer())
          .create();
      Retrofit retrofit = new Retrofit.Builder()
          .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
          .addConverterFactory(GsonConverterFactory.create(gson))
          .baseUrl(BuildConfig.BASE_URL)
          .client(client)
          .build();
      INSTANCE = retrofit.create(NetworkService.class);
    }


  }

  public static class DateDeserializer implements JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
        throws JsonParseException {
      long millis = json.getAsLong();
      return new Date(millis);
    }
  }

}
