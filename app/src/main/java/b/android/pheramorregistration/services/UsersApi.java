package b.android.pheramorregistration.services;

import b.android.pheramorregistration.models.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UsersApi {

    String BASE_URL = "https://external.dev.pheramor.com/";

    @POST("post")
    Call<POST> createUser(@Body User user);
}
