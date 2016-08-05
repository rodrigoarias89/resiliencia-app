package ar.com.lapotoca.resiliencia.ui;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.libraries.cast.companionlibrary.cast.VideoCastManager;
import com.google.android.libraries.cast.companionlibrary.cast.callbacks.VideoCastConsumerImpl;

import ar.com.lapotoca.resiliencia.DownloadMusicManager;
import ar.com.lapotoca.resiliencia.R;
import ar.com.lapotoca.resiliencia.utils.LogHelper;
import ar.com.lapotoca.resiliencia.utils.ShareHelper;

/**
 * Abstract activity with toolbar, navigation drawer and cast support. Needs to be extended by
 * any activity that wants to be shown as a top level activity.
 */
public abstract class ActionBarCastActivity extends AppCompatActivity {

    private static final String TAG = LogHelper.makeLogTag(ActionBarCastActivity.class);

    private VideoCastManager mCastManager;
    private Toolbar mToolbar;

    private final VideoCastConsumerImpl mCastConsumer = new VideoCastConsumerImpl() {

        @Override
        public void onFailed(int resourceId, int statusCode) {
            LogHelper.d(TAG, "onFailed ", resourceId, " status ", statusCode);
        }

        @Override
        public void onConnectionSuspended(int cause) {
            LogHelper.d(TAG, "onConnectionSuspended() was called with cause: ", cause);
        }

        @Override
        public void onConnectivityRecovered() {
        }

        @Override
        public void onCastAvailabilityChanged(boolean castPresent) {
        }

    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogHelper.d(TAG, "Activity onCreate");

        // Ensure that Google Play Service is available.
        VideoCastManager.checkGooglePlayServices(this);

        mCastManager = VideoCastManager.getInstance();
        mCastManager.reconnectSessionIfPossible();
    }

    @Override
    public void onResume() {
        super.onResume();
        mCastManager.addVideoCastConsumer(mCastConsumer);
        mCastManager.incrementUiCounter();
    }

    @Override
    public void onPause() {
        super.onPause();
        mCastManager.removeVideoCastConsumer(mCastConsumer);
        mCastManager.decrementUiCounter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        mCastManager.addMediaRouterButton(menu, R.id.media_route_menu_item);
        return true;
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        mToolbar.setTitle(title);
    }

    @Override
    public void setTitle(int titleId) {
        super.setTitle(titleId);
        mToolbar.setTitle(titleId);
    }

    protected void initializeToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        if (mToolbar != null) {
            mToolbar.inflateMenu(R.menu.main);
            setSupportActionBar(mToolbar);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_settings_compartir:
                ShareHelper.getInstance().shareContentOnFacebook(this);
                return true;
            case R.id.action_settings_descargar:
                if (DownloadMusicManager.getInstance() != null) {
                    DownloadMusicManager.getInstance().downloadAll(this);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            // Lastly, it will rely on the system behavior for back
            super.onBackPressed();
        }
    }


}