package proj.dummyexample;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class MainActivity extends Activity {

    private static int SPLASH_TIME_OUT = 3000;
    TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title= (TextView) findViewById(R.id.title);
        int[] color = {Color.DKGRAY, Color.CYAN};

        float[] position = {0, 1};
        Shader.TileMode tile_mode = Shader.TileMode.MIRROR; // or TileMode.REPEAT;
        LinearGradient lin_grad = new LinearGradient(0, 0, 0, 50,color,position, tile_mode);
        Shader shader_gradient = lin_grad;
        title.getPaint().setShader(shader_gradient);


        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(MainActivity.this, HomeScreen.class);
                startActivity(i);
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
