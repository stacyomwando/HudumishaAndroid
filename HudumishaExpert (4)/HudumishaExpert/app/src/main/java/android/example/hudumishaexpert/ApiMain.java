package android.example.hudumishaexpert;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.Query;

public interface ApiMain {

    @GET("SelectMain.php")
    Call<ExpertGet> selectMain(@Query("uid") String userid);
}