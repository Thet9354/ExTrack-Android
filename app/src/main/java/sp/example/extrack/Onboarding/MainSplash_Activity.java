package sp.example.extrack.Onboarding;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;

import com.example.extrack.R;

public class MainSplash_Activity extends AppCompatActivity {

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_splash);

        // For the "noice" GIF
        mediaPlayer = MediaPlayer.create(this, R.raw.learnloop_music);
        mediaPlayer.start();

        // Splash Page for 2 Seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(getApplicationContext(), Main_Onboarding_Activity.class);
                startActivity(intent);
                finish();
            }
        },2000);
    }
}