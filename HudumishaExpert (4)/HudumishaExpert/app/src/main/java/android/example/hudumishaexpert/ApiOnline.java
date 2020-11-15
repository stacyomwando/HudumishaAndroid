package android.example.hudumishaexpert;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiOnline {
    @GET("VendorOnline.php")
    Call<ResponseStatus> updateData(@Query("userid") String userid );
}

