package android.example.hudumishaexpert;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SpinnerInterface {
      ApiRetrofit url=new ApiRetrofit();
    //String JSONURL = "http://192.168.43.134/Hudumisha/";
    String JSONURL =url.getBASEURL();

    @GET("SelectCategory.php")
    Call<String> getJSONString();
}