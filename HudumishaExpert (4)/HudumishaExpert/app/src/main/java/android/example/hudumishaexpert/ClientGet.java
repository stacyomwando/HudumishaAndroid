package android.example.hudumishaexpert;

import android.graphics.Bitmap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClientGet {

    @Expose
    @SerializedName("response")
    private String Response;
    public String getResponse() {
        return Response;
    }

    @Expose
    @SerializedName("name")
    private String Name;
    public String getName() {
        return Name;
    }


    @Expose
    @SerializedName("skill")
    private String Skill;
    public String getSkill() {
        return Skill;
    }

    @Expose
    @SerializedName("fixes")
    private String Fixes;
    public String getFixes() {
        return Fixes;
    }

    @Expose
    @SerializedName("rating")
    private String Rating;
    public String getRating() {
        return Rating;
    }

    @Expose
    @SerializedName("phone")
    private String Phone;
    public String getPhone() {
        return Phone;
    }

    @Expose
    @SerializedName("email")
    private String Email;
    public String getEmail() {
        return Email;
    }


    @Expose
    @SerializedName("status")
    private String Status;
    public String getStatus() {
        return Status;
    }

    @Expose
    @SerializedName("account_status")
    private String account_status;
    public String getAccount_status() {
        return account_status;
    }
    @Expose
    @SerializedName("image")
    private String image;
    public String getImage() {
        return image;
    }

}



