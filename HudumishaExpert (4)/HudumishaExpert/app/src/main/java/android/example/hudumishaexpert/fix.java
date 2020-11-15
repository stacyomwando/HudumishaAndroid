package android.example.hudumishaexpert;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fix extends AppCompatActivity {
    Button direction, callbutton,startfix;

     TextView name,email;
     ImageView profile;
    UserId user=new UserId();
    // String user_id=user.getUser();
     String user_id="1";
     String  client_id="1";
     String phonenumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fix);

        direction = (Button) findViewById(R.id.direction);
        callbutton = (Button) findViewById(R.id.callbutton);
        startfix = (Button) findViewById(R.id.fixing);

        direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent myIntent = new Intent(getBaseContext(), OngoingFix.class);
                startActivity(myIntent);
            }
        });

        name = (TextView) findViewById(R.id.client_name);
        email = (TextView) findViewById(R.id.client_email);
        profile = (ImageView) findViewById(R.id.client_image);
        ApiRetrofit apiRetrofit = new ApiRetrofit();
        Client apimain = apiRetrofit.getRetrofit().create(Client.class);
        Call<ClientGet> call = apimain.selectClient(client_id);

        call.enqueue(new Callback<ClientGet>() {
            @Override
            public void onResponse(Call<ClientGet> call, Response<ClientGet> response) {
                if (response.body().getResponse().equals("success")) {
                    try {
                        ApiRetrofit apiurl = new ApiRetrofit();

                        Picasso.with(getApplicationContext())
                                .load(apiurl.getBASEURL() + "images/" + client_id + ".jpg")//Your image link url
                                .into(profile);
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Failed " + e, Toast.LENGTH_LONG).show();
                    }
                    Toast.makeText(getApplicationContext(), "Success ", Toast.LENGTH_LONG).show();
                    name.setText(response.body().getName());
                    name.setEnabled(false);
                    email.setText(response.body().getEmail());
                    email.setEnabled(false);
                   phonenumber=response.body().getPhone();
//                    name.setText("Maich");
//                    email.setText("Email");
                } else if (response.body().getResponse().equals("failed")) {
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ClientGet> call, Throwable t) {
                //display errror message
                Toast.makeText(getApplicationContext(), "There was an Error Connecting to the Servers.Please Try Again " + t, Toast.LENGTH_LONG).show();
                Intent myIntent = new Intent(getBaseContext(), login.class);
                startActivity(myIntent);
            }
        });


        callbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onCall();

            }
        });


//
//        startfix.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(final View v) {
//
//
//
//                if(startfix.getText().equals("START FIX")||startfix.getText().equals("start fix")) {
//                    String fix="complete";
//                    ApiRetrofit api = new ApiRetrofit();
//
//                    Fixing fixing = api.getRetrofit().create(Fixing.class);
//
//                    ResponseStatus responseStatus = new ResponseStatus();
//                    Call<ResponseStatus> call = fixing.updatefix(user_id,fix);
//                    call.enqueue(new Callback<ResponseStatus>() {
//                        @Override
//                        public void onResponse(Call<ResponseStatus> call, Response<ResponseStatus> response) {
//                            if (response.body().getResponse().equals("success")) {
//                                startfix.setText("COMPLETE FIX");
//                            }
//                            else if (response.body().getResponse().equals("failed")) {
//                                Toast.makeText(getApplicationContext(), "Unable to connect to the Servers.Please try again", Toast.LENGTH_LONG).show();
//
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<ResponseStatus> call, Throwable t) {
//
//                            Toast.makeText(getApplicationContext(), "There was an Error Connecting to the Servers.Please Try Again " + t, Toast.LENGTH_LONG).show();
//                        }
//                    });
//
//                }
//                else if(startfix.getText().equals("COMPLETE FIX")||startfix.getText().equals("complete fix")){
//                 String fix="complete";
//
//
//                     ApiRetrofit api=new ApiRetrofit();
//                    Fixing fixing = api.getRetrofit().create(Fixing.class);
//
//                    ResponseStatus responseStatus = new ResponseStatus();
//                    Call<ResponseStatus> call = fixing.updatefix(user_id,fix);
//                    call.enqueue(new Callback<ResponseStatus>() {
//                        @Override
//                        public void onResponse(Call<ResponseStatus> call, Response<ResponseStatus> response) {
//                            if (response.body().getResponse().equals("success")) {
//                                startfix.setText("START FIX");
//
//
//
//                            } else if (response.body().getResponse().equals("failed")) {
//
//                                Toast.makeText(getApplicationContext(), "Unable to connect to the Servers.Please try again", Toast.LENGTH_LONG).show();
//
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<ResponseStatus> call, Throwable t) {
//
//                            Toast.makeText(getApplicationContext(), "There was an Error Connecting to the Servers.Please Try Again " + t, Toast.LENGTH_LONG).show();
//                        }
//                    });
//
//                }
//                else{
//                    Toast.makeText(getApplicationContext(), "There was an Error."+startfix.getText(), Toast.LENGTH_LONG).show();
//
//                }
//            }
//        });


    }
        public void onCall () {
            int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);

            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        Integer.parseInt("123"));
            } else {
                startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:"+phonenumber)));
            }
        }


        @Override
        public void onRequestPermissionsResult ( int requestCode, @NonNull String[] permissions,
        @NonNull int[] grantResults){
            switch (requestCode) {

                case 123:
                    if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                        onCall();
                    } else {
                        Log.d("TAG", "Call Permission Not Granted");
                    }
                    break;

                default:
                    break;
            }
        }



}