package android.example.hudumishaexpert;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExistUser {
        @Expose
        @SerializedName("response")
        private String Response;
        public String getResponse() {
            return Response;
        }

        @Expose
        @SerializedName("userid")
        private String Userid;
        public String getUserid() {
                return Userid;
        }




}
