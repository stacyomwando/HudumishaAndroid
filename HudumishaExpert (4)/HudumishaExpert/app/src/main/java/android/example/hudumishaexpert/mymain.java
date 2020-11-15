package android.example.hudumishaexpert;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class mymain extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Button online;
    ProgressBar loading;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mymain);
//        setContentView(R.layout.nav_header_mymain);
        dialog = new ProgressDialog(mymain.this);
        dialog.setMessage("Loading your Information, please wait.");
        dialog.show();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.hide();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        final TextView name,skill,rating,fixes,status,email,phone,navname;
        final ImageView profile,profile2;
        UserId user=new UserId();
        final String user_id=user.getUser();

        fixes = (TextView) findViewById(R.id.text_fixes);
        name = (TextView) findViewById(R.id.text_name);
//        navname = (TextView) findViewById(R.id.my_name);
        skill = (TextView) findViewById(R.id.text_skill);
        rating = (TextView) findViewById(R.id.text_rating);
        status = (TextView) findViewById(R.id.text_status);
        email = (TextView) findViewById(R.id.text_email);
        phone = (TextView) findViewById(R.id.text_phone);

        profile = (ImageView) findViewById(R.id.imgpic);
        profile2 = (ImageView) findViewById(R.id.image_View);




        name.setText("");
//        navname.setText("");
        fixes.setText("");
        rating.setText("");
        skill.setText("");
        status.setText("");
        email.setText("");
        phone.setText("");




//        ApiMain apimain = ApiClient.getApiClient().create(ApiMain.class);
        ApiRetrofit api = new ApiRetrofit();
        ApiMain apimain = api.getRetrofit().create(ApiMain.class);
        Call<ExpertGet> call =apimain.selectMain(user_id);
        call.enqueue(new Callback<ExpertGet>() {
            @Override
            public void onResponse(Call<ExpertGet> call, Response<ExpertGet> response) {
                if(response.body().getResponse().equals("success")){
                    if(response.body().getImage().equals("none")){
                        Intent myIntent = new Intent(getBaseContext(), Form_2.class);
                        startActivity(myIntent);
                    }
                    else{
                        try {
                            ApiRetrofit apiurl = new ApiRetrofit();

                            Picasso.with(getApplicationContext())
                                    .load(apiurl.getBASEURL()+"images/"+user_id+".jpg")//Your image link url
                                    .into(profile);
                        }
                        catch(Exception e){
                            Toast.makeText(getApplicationContext(), "Failed "+e , Toast.LENGTH_LONG).show();
                        }
//                        try {
//                            Picasso.with(getApplicationContext())
//                                    .load("http://192.168.43.134/Hudumisha/images/"+user_id+".jpg")//Your image link url
//                                    .into(profile2);
//                        }
//                        catch(Exception z){
//                            Toast.makeText(getApplicationContext(), "Failed "+z , Toast.LENGTH_LONG).show();
//                        }
                        name.setText(response.body().getName());
//                        navname.setText(response.body().getName());
                        fixes.setText(response.body().getFixes());
                        rating.setText(response.body().getRating());
                        skill.setText(response.body().getSkill());
                        status.setText("Status: "+response.body().getStatus());
                        email.setText("E-mail: "+response.body().getEmail() );
                        //   email.setText("http://192.168.43.134/Hudumisha/images/"+user_id+".jpg");
                        phone.setText("Contact:  "+response.body().getPhone());
//                        isNetworkAvailable();



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


        loading = (ProgressBar)findViewById(R.id.progressBar4);
        loading.setVisibility(View.GONE);
        online = (Button) findViewById(R.id.go_online);
        String statt=status.getText().toString();
        if(statt.equals("Status: online")){
            online.setText("GO OFFLINE");

//            online.setEnabled(false);
//            online.setClickable(false);
        }
        else{
            online.setText("GO ONLINE");
//            online.setEnabled(false);
//            online.setClickable(false);
        }

        dialog.dismiss();

        online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                String button_status=online.getText().toString();

                if(button_status.equals("GO ONLINE")) {
                    online.setVisibility(v.INVISIBLE);
                    loading.setVisibility(View.VISIBLE);
                    ApiRetrofit api = new ApiRetrofit();

                    ApiOnline apionline = api.getRetrofit().create(ApiOnline.class);
                    ResponseStatus goOnline = new ResponseStatus();
                    Call<ResponseStatus> call = apionline.updateData(user_id);
                    call.enqueue(new Callback<ResponseStatus>() {
                        @Override
                        public void onResponse(Call<ResponseStatus> call, Response<ResponseStatus> response) {
                            if (response.body().getResponse().equals("success")) {
                                loading.setVisibility(View.GONE);
                                online.setVisibility(v.VISIBLE);
                                status.setText("Status: online");
                                online.setText("GO OFFLINE");


                            } else if (response.body().getResponse().equals("failed")) {
                                loading.setVisibility(View.GONE);
                                online.setVisibility(v.VISIBLE);
                                Toast.makeText(getApplicationContext(), "Unable to connect to the Servers.Please try again", Toast.LENGTH_LONG).show();

                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseStatus> call, Throwable t) {
                            //display errror message
                            loading.setVisibility(View.GONE);
                            online.setVisibility(v.VISIBLE);
                            Toast.makeText(getApplicationContext(), "There was an Error Connecting to the Servers.Please Try Again " + t, Toast.LENGTH_LONG).show();
                        }
                    });

                }
                else if(button_status.equals("GO OFFLINE")){
                    online.setVisibility(v.INVISIBLE);
                    loading.setVisibility(View.VISIBLE);
                    ApiRetrofit api = new ApiRetrofit();


                    ApiOffline apioffline = api.getRetrofit().create(ApiOffline.class);
                    ResponseStatus responseStatus = new ResponseStatus();
                    Call<ResponseStatus> call = apioffline.update_Data(user_id);
                    call.enqueue(new Callback<ResponseStatus>() {
                        @Override
                        public void onResponse(Call<ResponseStatus> call, Response<ResponseStatus> response) {
                            if (response.body().getResponse().equals("success")) {
                                loading.setVisibility(View.GONE);
                                online.setVisibility(v.VISIBLE);
                                status.setText("Status: offline");
                                online.setText("GO ONLINE");



                            } else if (response.body().getResponse().equals("failed")) {
                                loading.setVisibility(View.GONE);
                                online.setVisibility(v.VISIBLE);
                                Toast.makeText(getApplicationContext(), "Unable to connect to the Servers.Please try again", Toast.LENGTH_LONG).show();

                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseStatus> call, Throwable t) {
                            //display errror message
                            loading.setVisibility(View.GONE);
                            online.setVisibility(v.VISIBLE);
                            Toast.makeText(getApplicationContext(), "There was an Error Connecting to the Servers.Please Try Again " + t, Toast.LENGTH_LONG).show();
                        }
                    });

                }
                else{
                    Toast.makeText(getApplicationContext(), "There was an Error.", Toast.LENGTH_LONG).show();

                }
            }
        });





    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mymain, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

//        if (id == R.id.nav_home) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_tools) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }
        if (id == R.id.nav_edit) {
            Intent myIntent = new Intent(getBaseContext(), ExpertMain.class);
            startActivity(myIntent);

        }
        else if (id == R.id.nav_ongoingfix) {
            Intent myIntent = new Intent(getBaseContext(), fix.class);
            startActivity(myIntent);

        }
        else if (id == R.id.nav_completedfixes) {
            Intent myIntent = new Intent(getBaseContext(), login.class);
            startActivity(myIntent);

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


//    private boolean isNetworkAvailable() {
//        ConnectivityManager connectivityManager
//                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
//        if(activeNetworkInfo != null && activeNetworkInfo.isConnected()){
//            return true;
//        }
//        else {
//
////            Snackbar.make(,"You're Offline", Snackbar.LENGTH_LONG)
////                    .setAction("Action", null).show();
//            return false;
//        }
 //   }
}
