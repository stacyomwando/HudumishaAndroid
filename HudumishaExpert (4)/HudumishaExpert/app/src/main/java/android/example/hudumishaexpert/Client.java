package android.example.hudumishaexpert;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.Query;

public interface Client {

    @GET("SelectClient.php")
    Call<ClientGet> selectClient(@Query("client_id") String userid);
}
