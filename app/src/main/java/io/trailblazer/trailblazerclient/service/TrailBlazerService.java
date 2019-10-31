package io.trailblazer.trailblazerclient.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.trailblazer.trailblazerclient.BuildConfig;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public interface TrailBlazerService {

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
