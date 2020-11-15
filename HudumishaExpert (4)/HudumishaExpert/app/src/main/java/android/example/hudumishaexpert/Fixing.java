package android.example.hudumishaexpert;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Fixing {
    //@GET("vendorfixing.php")
   // Call<ResponseStatus> updatefix(@Query("userid") String userid,@Query("status") String fixing);
    //Call<ResponseStatus> updatefix(@Query("userid") String userid);

    @FormUrlEncoded
    @POST("vendorfixing.php")
    Call<ResponseStatus> updatefix(@Field("userid") String userid, @Field("status") String fixing);
}

