package android.example.hudumishaexpert;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Edit {
    @FormUrlEncoded
    @POST("vendoredit.php")
    Call<ResponseStatus> updateData(@Field("userid") String userid, @Field("email") String email, @Field("phone") String phone );
}

