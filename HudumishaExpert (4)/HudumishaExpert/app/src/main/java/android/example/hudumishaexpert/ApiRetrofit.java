package android.example.hudumishaexpert;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiRetrofit {



    //private static final String BASE_URL="http://10.51.48.101/Hudumisha/";
   // private String BASE_URL="http://192.168.43.194:8000/";
    private String BASE_URL="http://192.168.43.134/Hudumisha/";
    private Retrofit retrofit;


    public String getBASEURL() {
        return BASE_URL;
    }

    public void setBASEURL(String BASE_URL) {
        this.BASE_URL = BASE_URL;
    }

    public Retrofit getRetrofit() {
        OkHttpClient client = new OkHttpClient();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit;
    }





}
