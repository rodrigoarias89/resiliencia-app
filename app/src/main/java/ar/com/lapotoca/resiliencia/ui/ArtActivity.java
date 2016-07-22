package ar.com.lapotoca.resiliencia.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ar.com.lapotoca.resiliencia.R;

public class ArtActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_art);
        initializeToolbar();
    }
}
