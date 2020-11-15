package android.example.hudumishaexpert;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseStatus {
    @Expose
    @SerializedName("response")
    private String Response;
    public String getResponse() {
        return Response;
    }

}
