package ar.com.lapotoca.resiliencia.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * The activity for the Now Playing Card PendingIntent.
 * https://developer.android.com/training/tv/playback/now-playing.html
 *
 * This activity determines which activity to launch based on the current UI mode.
 */
public class NowPlayingActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent newIntent;
        newIntent = new Intent(this, MusicPlayerActivity.class);
        startActivity(newIntent);
        finish();
    }
}
