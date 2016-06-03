package in.mobileappdev.rest;

import in.mobileappdev.models.VideosResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by satyanarayana.avv on 05-04-2016.
 */
public interface RestApi {

  @FormUrlEncoded
  @POST("api.php")
  Call<VideosResponse> getVideos(@Field("tag") String tag);



}
