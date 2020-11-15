package android.example.hudumishaexpert;

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

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUp extends AppCompatActivity {
    EditText name,email,password,confirm_password,phone;
    Button save;
    private ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        loading = (ProgressBar)findViewById(R.id.progressBar);
        loading.setVisibility(View.GONE);
      name = (EditText) findViewById(R.id.vendor_name);
         email = (EditText) findViewById(R.id.vendor_email);
        phone = (EditText) findViewById(R.id.vendor_number);
        password = (EditText) findViewById(R.id.password);
        confirm_password = (EditText) findViewById(R.id.confirm_password);
         save = (Button) findViewById(R.id.button3);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Do something in response to button click
                save.setVisibility(v.INVISIBLE);
                save.setVisibility(View.VISIBLE);


                if ((name.getText().toString().isEmpty())||(name.getText().toString().equals(null))){
                    loading.setVisibility(View.GONE);
                    name.setError("Please Fill Out Your Name");
                }
               else if ((email.getText().toString().isEmpty())||(email.getText().toString().equals( null))) {
                    loading.setVisibility(View.GONE);
                    email.setError("Please Fill Out Your Email");
                }

               else if((phone.getText().toString().isEmpty())||(phone.getText().toString().equals( null))) {
                    loading.setVisibility(View.GONE);
                    phone.setError("Please Fill Out Your Phone");
                }
               else if((password.getText().toString().isEmpty())||(password.getText().toString().equals(null))){
                    loading.setVisibility(View.GONE);
                    password.setError("Please Fill Out Your Password");
                }
               else if((confirm_password.toString().isEmpty())||(confirm_password.getText().toString().equals(null))){
                    loading.setVisibility(View.GONE);
                    confirm_password.setError("Please Confirm Your Passord");
                }
                else{
                   if(password.getText().toString().equals(confirm_password.getText().toString())){
                       ApiRetrofit api=new ApiRetrofit();
                       ApiService service = api.getRetrofit().create(ApiService.class);
                       NewUser newuser = new NewUser();
                       newuser.setName(name.getText().toString());
                       newuser.setEmail(email.getText().toString());
                       newuser.setPhone(phone.getText().toString());
                       newuser.setPassword(password.getText().toString());
                       Call<NewUser> call = service.insertData(newuser);

                       call.enqueue(new Callback<NewUser>() {
                           @Override
                           public void onResponse(Call<NewUser> call, Response<NewUser> response) {

                               if(response.body().getResponse().equals("success")){
                                   loading.setVisibility(View.GONE);
                                   UserId uid=new UserId();
                                   uid.setUser(response.body().getUser());
                                   Toast.makeText(getApplicationContext(), "Successfully Created Account", Toast.LENGTH_SHORT).show();
                                   Intent myIntent = new Intent(getBaseContext(), Form_2.class);
                                   startActivity(myIntent);
                               }
                               else if(response.body().getResponse().equals("failed")){
                                   loading.setVisibility(View.GONE);
                                   Toast.makeText(getApplicationContext(),"The Account already Exists", Toast.LENGTH_SHORT).show();
                                   name.setText("");
                                   email.setText("");
                                   phone.setText("");
                                   password.setText("");
                                   confirm_password.setText("");
                               }

                           }

                           @Override
                           public void onFailure(Call<NewUser> call, Throwable t) {
                               loading.setVisibility(View.GONE);
                               Log.i("Error is! ", "" + t.getMessage());
                               Toast.makeText(getApplicationContext(), "Throwable" + t, Toast.LENGTH_LONG).show();


                           }
                       });


                   }
                   else{
                       loading.setVisibility(View.GONE);
                       confirm_password.setError("Confirm Passord Does not Match the Password");
                }
            }
            }
    });
}
}
