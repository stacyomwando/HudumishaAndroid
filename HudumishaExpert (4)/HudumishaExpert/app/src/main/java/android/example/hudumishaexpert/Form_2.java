package android.example.hudumishaexpert;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static android.R.layout.simple_spinner_item;

public class Form_2 extends AppCompatActivity {


    Bitmap bitmap;
    ImageView imageView;
    Button selectImg,uploadImg;

    private  static final int IMAGE = 100;

    private ArrayList<SpinnerModel> goodModelArrayList;
    private ArrayList<String> playerNames = new ArrayList<String>();
    private Spinner spinner;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_2);
        imageView = (ImageView) findViewById(R.id.imgpic);
        selectImg = (Button) findViewById(R.id.button_id2);
        uploadImg = (Button) findViewById(R.id.button);

        spinner = findViewById(R.id.expert_service);
        fetchJSON();


        selectImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectImage();

            }
        });

        uploadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadImage();

            }
        });

    }


    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE);
    }

    private String convertToString()
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte,Base64.DEFAULT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== IMAGE && resultCode==RESULT_OK && data!=null)
        {
            Uri path = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage(){

        String image = convertToString();
        UserId uid=new UserId();
        String profile_image =uid.getUser();
        String services = spinner.getSelectedItem().toString();


        //String imageName ="29";
        ApiRetrofit api=new ApiRetrofit();
        ApiForm2 apiInterface = api.getRetrofit().create(ApiForm2.class);


            Call<Img_Pojo> call = apiInterface.uploadImage(profile_image,image,services);

            call.enqueue(new Callback<Img_Pojo>() {
                @Override
                public void onResponse(Call<Img_Pojo> call, Response<Img_Pojo> response) {

                    Img_Pojo img_pojo = response.body();
                    Log.d("Response", img_pojo.getResponse());
            Toast.makeText(getApplicationContext(),img_pojo.getResponse(), Toast.LENGTH_LONG).show();
                    Intent myIntent = new Intent(getBaseContext(), mymain.class);
                    startActivity(myIntent);

                }

                @Override
                public void onFailure(Call<Img_Pojo> call, Throwable t) {
                    Log.d("Server Response", "" + t.toString());
                    Toast.makeText(getApplicationContext(), "There was an Error Connecting to the Servers.Please Try Again " + t.toString(), Toast.LENGTH_LONG).show();

                }
            });


        }




    private void fetchJSON(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SpinnerInterface.JSONURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        SpinnerInterface api = retrofit.create(SpinnerInterface.class);

        Call<String> call = api.getJSONString();

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("Responsestring", response.body().toString());
                //Toast.makeText()
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i("onSuccess", response.body().toString());

                        String jsonresponse = response.body().toString();
                        spinJSON(jsonresponse);

                    } else {
                        Log.i("onEmptyResponse", "Returned empty response");//Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                //Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(),t.toString(),Toast.LENGTH_LONG).show();

            }
        });
    }

    private void spinJSON(String response){

        try {

            JSONObject obj = new JSONObject(response);
            if(obj.optString("status").equals("true")){

                goodModelArrayList = new ArrayList<>();
                JSONArray dataArray  = obj.getJSONArray("data");

                for (int i = 0; i < dataArray.length(); i++) {

                    SpinnerModel spinnerModel = new SpinnerModel();
                    JSONObject dataobj = dataArray.getJSONObject(i);

                    spinnerModel.setCategoryName(dataobj.getString("category_name"));


                    goodModelArrayList.add(spinnerModel);

                }

                for (int i = 0; i < goodModelArrayList.size(); i++){
                    playerNames.add(goodModelArrayList.get(i).getCategoryName().toString());
                }

                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(Form_2.this, simple_spinner_item, playerNames);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                spinner.setAdapter(spinnerArrayAdapter);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }



}

