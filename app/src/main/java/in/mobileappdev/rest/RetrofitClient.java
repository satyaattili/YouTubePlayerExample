package in.mobileappdev.rest;

import android.content.Context;

import in.mobileappdev.utils.Config;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by satyanarayana.avv on 05-04-2016.
 */
public class RetrofitClient {

  private static RestApi apiService;
  private static Context mContext;

  private static Retrofit getRetroFit() {

    return new Retrofit.Builder()
        .baseUrl(Config.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
  }

  public static RestApi getApiService(Context ctx) {
    mContext = ctx;
    if (apiService == null) {
      apiService = getRetroFit().create(RestApi.class);
    }
    return apiService;
  }



}
