package android.example.hudumishaexpert;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExpertMain extends AppCompatActivity {
Button online;
ProgressBar loading;
       String client_email,client_phone;
     TextView name,email,phone;
     ImageView profile;
    UserId user=new UserId();
     String user_id=user.getUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_main);




       name = (TextView) findViewById(R.id.text_name);



       email = (TextView) findViewById(R.id.text_email);
       phone = (TextView) findViewById(R.id.text_phone);

    profile = (ImageView) findViewById(R.id.imgpic);




        name.setText("");
        email.setText("");
        phone.setText("");




//        ApiMain apimain = ApiClient.getApiClient().create(ApiMain.class);
        ApiRetrofit api = new ApiRetrofit();
                    ApiMain apimain = api.getRetrofit().create(ApiMain.class);
                    Call<ExpertGet> call =apimain.selectMain(user_id);
                    call.enqueue(new Callback<ExpertGet>() {
                        @Override            public void onResponse(Call<ExpertGet> call, Response<ExpertGet> response) {
                            if(response.body().getResponse().equals("success")){
                                if(response.body().getImage().equals("none")){
                                    Intent myIntent = new Intent(getBaseContext(), home.class);
                                    startActivity(myIntent);
                                }
                                    else{
                                try {
                                    Picasso.with(getApplicationContext())
                                            .load("http://192.168.43.134/Hudumisha/images/"+user_id+".jpg")//Your image link url
                                            .into(profile);
                                }
                                catch(Exception e){
                                    Toast.makeText(getApplicationContext(), "Failed "+e , Toast.LENGTH_LONG).show();
                                }
                                name.setText(response.body().getName());
                                 client_email=response.body().getEmail();
                                 client_phone=response.body().getPhone();


                                email.setText(response.body().getEmail() );
                             //   email.setText("http://192.168.43.134/Hudumisha/images/"+user_id+".jpg");
                                phone.setText(response.body().getPhone());



//                                if(response.body().getAccount_status().equals("approved")){
//                                    online.setEnabled(true);
//                                    online.setClickable(true);
//                                }

//                            profile.setImageBitmap(response.body().getImage());


                             }
                            }
                            else if(response.body().getResponse().equals("failed")){
                                Toast.makeText(getApplicationContext(), "Failed" , Toast.LENGTH_LONG).show();
                            }
                        }
                        @Override            public void onFailure(Call<ExpertGet> call, Throwable t) {
                            //display errror message
                            Toast.makeText(getApplicationContext(), "There was an Error Connecting to the Servers.Please Try Again "+t , Toast.LENGTH_LONG).show();
                            Intent myIntent = new Intent(getBaseContext(), login.class);
                            startActivity(myIntent);
                        }
                    });



        online = (Button) findViewById(R.id.go_online);




        online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {



                ApiRetrofit api = new ApiRetrofit();

                Edit apionline = api.getRetrofit().create(Edit.class);
                ResponseStatus goOnline = new ResponseStatus();
                Call<ResponseStatus> call = apionline.updateData(user_id,client_email,client_phone);
                call.enqueue(new Callback<ResponseStatus>() {
                    @Override
                    public void onResponse(Call<ResponseStatus> call, Response<ResponseStatus> response) {
                        if (response.body().getResponse().equals("success")) {
                            Toast.makeText(getApplicationContext(), "Successfully Made Changes", Toast.LENGTH_LONG).show();


                        } else if (response.body().getResponse().equals("failed")) {

                            Toast.makeText(getApplicationContext(), "Unable to connect to the Servers.Please try again", Toast.LENGTH_LONG).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseStatus> call, Throwable t) {
                        //display errror messageloading.setVisibility(View.GONE);

                        Toast.makeText(getApplicationContext(), "There was an Error Connecting to the Servers.Please Try Again " + t, Toast.LENGTH_LONG).show();
                    }
                });

            }

        });

    }

            }





