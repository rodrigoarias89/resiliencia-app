package ar.com.lapotoca.resiliencia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ar.com.lapotoca.resiliencia.ui.MusicPlayerActivity;

/**
 * Created by rarias on 8/9/16.
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, MusicPlayerActivity.class);
        startActivity(intent);
        finish();
    }
}