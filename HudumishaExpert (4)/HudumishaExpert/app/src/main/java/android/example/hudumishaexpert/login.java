package android.example.hudumishaexpert;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class login extends AppCompatActivity {
    EditText email,password;
    Button login,signup;
    private ProgressBar loading;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signup = (Button) findViewById(R.id.sign_up);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent myIntent = new Intent(getBaseContext(), SignUp.class);
                startActivity(myIntent);
            }
        });
        loading = (ProgressBar)findViewById(R.id.progressBar1);
        loading.setVisibility(View.GONE);
        email = (EditText) findViewById(R.id.login_email);
        password = (EditText) findViewById(R.id.login_password);

        login = (Button) findViewById(R.id.login_button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                login.setVisibility(v.INVISIBLE);
                loading.setVisibility(View.VISIBLE);

                if ((email.getText().toString().isEmpty())||(email.getText().toString().equals(null))){
                    loading.setVisibility(View.GONE);
                    email.setError("Please Fill Out Your Name");
                }
                else if ((password.getText().toString().isEmpty())||(password.getText().toString().equals( null))) {
                    loading.setVisibility(View.GONE);
                    password.setError("Please Fill Out Your Email");
                }
                else{

                    ApiRetrofit api = new ApiRetrofit();
                    ApiLogin apilogin = api.getRetrofit().create(ApiLogin.class);

                    String final_password=password.getText().toString();
                    String final_email =email.getText().toString();

                    Call<ExistUser> call =apilogin.selectData(final_email,final_password);
                    call.enqueue(new Callback<ExistUser>() {
                        @Override
                        public void onResponse(Call<ExistUser> call, Response<ExistUser> response) {
                            if(response.body().getResponse().equals("success")){
                                UserId uid=new UserId();
                                uid.setUser(response.body().getUserid());

                                loading.setVisibility(View.GONE);
                                login.setVisibility(v.VISIBLE);
                               // Intent myIntent = new Intent(getBaseContext(),ExpertMain.class);
//                                Intent myIntent = new Intent(getBaseContext(),mymain.class);
//                                startActivity(myIntent);
                                handleLoginClick();

                            }
                            else if(response.body().getResponse().equals("failed")){

                                loading.setVisibility(View.GONE);
                                email.setText("");
                                password.setText("");
                                login.setVisibility(v.VISIBLE);
                                Toast.makeText(getApplicationContext(), "Incorrect Email or Password.Please Try Again" , Toast.LENGTH_LONG).show();

                            }
                        }
                        @Override            public void onFailure(Call<ExistUser> call, Throwable t) {
                            //display errror message
                            loading.setVisibility(View.GONE);
                            login.setVisibility(v.VISIBLE);
                            Toast.makeText(getApplicationContext(), "There was an Error Connecting to the Servers.Please Try Again "+t , Toast.LENGTH_LONG).show();
                        }
                    });

                }

                }


    });
}

    private void handleLoginClick(){
        Intent myIntent = new Intent(getBaseContext(),mymain.class);
        startActivity(myIntent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        email.setText("");
        password.setText("");
        finish();
    }
}
