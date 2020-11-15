package android.example.hudumishaexpert;

import com.google.gson.annotations.SerializedName;

class Img_Pojo {

    @SerializedName("image_name")
    private String Title;

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getTitle() {
        return Title;
    }

    @SerializedName("user_id")
    private String userid;

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserid() {
        return userid;
    }

    @SerializedName("image")
    private String Image;

    public void setImage(String Image) {
        this.Image = Image;
    }

    public String getImage() {
        return Image;
    }

    @SerializedName("services")
    private String Service;

    public void setService(String Service) {
        this.Service = Service;
    }

    public String getService() {
        return Service;
    }

    @SerializedName("response")
    private String Response;

    public String getResponse() {
        return Response;
    }
}