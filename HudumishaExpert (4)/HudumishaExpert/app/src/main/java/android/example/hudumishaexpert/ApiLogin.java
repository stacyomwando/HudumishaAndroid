package android.example.hudumishaexpert;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiLogin {
    @GET("SelectVendor.php")
    Call<ExistUser> selectData(@Query("email") String email, @Query("password") String pass);
}
