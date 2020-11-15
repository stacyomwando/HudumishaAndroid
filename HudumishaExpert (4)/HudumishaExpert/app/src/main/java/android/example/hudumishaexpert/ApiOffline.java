package android.example.hudumishaexpert;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiOffline {
    @GET("VendorOffline.php")
    Call<ResponseStatus> update_Data(@Query("userid") String userid );

}
