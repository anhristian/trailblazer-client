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
import com.vividsolutions.jts.geom.Geometry;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.trailblazer.trailblazerclient.BuildConfig;
import io.trailblazer.trailblazerclient.model.Trail;
import io.trailblazer.trailblazerclient.model.UserCharacteristics;
import java.lang.reflect.Type;
import java.util.Date;
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

/**
 * The interface Network service.
 */
public interface NetworkService {

  /**
   * Gets singleton instance.
   *
   * @return the instance
   */
  static NetworkService getInstance() {
    return NetworkService.InstanceHolder.INSTANCE;
  }

  /**
   * Gets all trails.
   *
   * @return the all trails
   */
  @GET("trails/public")
  Observable<List<Trail>> getAllTrails();

  /**
   * Gets my trails.
   *
   * @param token the token
   * @return the my trails
   */
  @GET("trails/mytrails")
  Observable<List<Trail>> getMyTrails(@Header("Authorization") String token);

  /**
   * Gets trails by name.
   *
   * @param token the token
   * @param name  the name
   * @return the trails by name
   */
  @GET("trails/search")
  Observable<List<Trail>> getTrailsByName(@Header("Authorization") String token,
      @Query("name") String name);

  /**
   * Gets all authenticated.
   *
   * @param token the token
   * @return the all authenticated
   */
  @GET("trails/")
  Observable<List<Trail>> getAllAuthenticated(@Header("Authorization") String token);

  /**
   * Gets trail by id.
   *
   * @param token the token
   * @param id    the id
   * @return the trail by id
   */
  @GET("trails/{id}")
  Single<Trail> getTrailById(@Header("Authorization") String token, @Path("id") long id);

  /**
   * Gets geometry.
   *
   * @param token the token
   * @param id    the id
   * @return the geometry
   */
  @GET("{id}/geometry")
  Observable<Geometry> getGeometry(@Header("Authorization") String token, @Path("id") long id);

  /**
   * Gets user.
   *
   * @param token the token
   * @return the user
   */

  @GET("user")
  Single<UserCharacteristics> getUser(@Header("Authorization") String token);

  /**
   * Update a single user characteristics.
   *
   * @param token               the token
   * @param userCharacteristics the user characteristics
   * @return the single
   */
  @PUT("user")
  Single<UserCharacteristics> updateUser(@Header("Authorization") String token,
      @Body UserCharacteristics userCharacteristics);

  /**
   * Post single trail .
   *
   * @param token the token
   * @param trail the trail
   * @return the single
   */
  @POST("trails")
  Single<Trail> postTrail(@Header("Authorization") String token, @Body Trail trail);


  /**
   * The type Instance holder.
   */
  class InstanceHolder {


    private static final NetworkService INSTANCE;

    static {
//      HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//      interceptor.setLevel(Level.BODY);
//      OkHttpClient client = new OkHttpClient.Builder()
//          .addInterceptor(interceptor)
//          .build();

      Gson gson = new GsonBuilder()
          .excludeFieldsWithoutExposeAnnotation()
          .registerTypeAdapter(Date.class, new DateDeserializer())
          .create();
      Retrofit retrofit = new Retrofit.Builder()
          .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
          .addConverterFactory(GsonConverterFactory.create(gson))
          .baseUrl(BuildConfig.BASE_URL)
//          .client(client)
          .build();
      INSTANCE = retrofit.create(NetworkService.class);
    }

  }

  class DateDeserializer implements JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
        throws JsonParseException {
      long millis = json.getAsLong();
      return new Date(millis);
    }
  }

}
