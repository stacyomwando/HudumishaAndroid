package android.example.hudumishaexpert;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiForm2 {
    @FormUrlEncoded
    @POST("fileUpload.php")
    Call<Img_Pojo> uploadImage(@Field("image_name") String title, @Field("image") String image,@Field("services") String Service);
}
