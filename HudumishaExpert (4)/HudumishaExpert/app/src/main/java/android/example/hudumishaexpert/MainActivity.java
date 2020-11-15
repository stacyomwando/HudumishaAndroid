package android.example.hudumishaexpert;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView xv;
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        xv=(TextView) findViewById(R.id.xv);
        iv=(ImageView) findViewById(R.id.iv);
        Animation myanim= AnimationUtils.loadAnimation(this,R.anim.start_transition);
        xv.startAnimation(myanim);
        iv.startAnimation(myanim);
        final Intent i= new Intent (this,login.class);
       //final Intent i= new Intent (this,fix.class);
       //final Intent i= new Intent (this,OngoingFix.class);
        Thread timer=new Thread(){
            public void run(){
                try{
                    sleep(2000);
                }
                catch(InterruptedException e){
                    e.printStackTrace();
                }
                finally{
                    startActivity(i);
                    finish();
                }
            }
        };
        timer.start();
    }
}
