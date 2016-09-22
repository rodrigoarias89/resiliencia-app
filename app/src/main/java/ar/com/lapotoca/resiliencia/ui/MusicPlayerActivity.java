package ar.com.lapotoca.resiliencia.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import ar.com.lapotoca.resiliencia.DownloadMusicManager;
import ar.com.lapotoca.resiliencia.R;
import ar.com.lapotoca.resiliencia.ui.custom.TabsAdapter;
import ar.com.lapotoca.resiliencia.utils.AnalyticsHelper;
import ar.com.lapotoca.resiliencia.utils.NotificationHelper;
import ar.com.lapotoca.resiliencia.utils.ShareHelper;

/**
 * Main activity for the music player.
 * This class hold the MediaBrowser and the MediaController instances. It will create a MediaBrowser
 * when it is created and connect/disconnect on start/stop. Thus, a MediaBrowser will be always
 * connected while this activity is running.
 */
public class MusicPlayerActivity extends BaseActivity implements AppBarLayout.OnOffsetChangedListener, MediaFragmentListener{

    private static final int PERCENTAGE_TO_ANIMATE_AVATAR = 20;
    private boolean mIsAvatarShown = true;

    private ImageView mProfileImage;
    private int mMaxScrollSize;

    private static final String ACTIVITY_NAME = MusicPlayerActivity.class.getSimpleName();
    private static final String SAVED_MEDIA_ID = "ar.com.lapotoca.resiliencia.MEDIA_ID";
    public static final String FRAGMENT_TAG = "resiliencia_list_container";

    public static final String EXTRA_START_FULLSCREEN =
            "ar.com.lapotoca.resiliencia.EXTRA_START_FULLSCREEN";

    /**
     * Optionally used with {@link #EXTRA_START_FULLSCREEN} to carry a MediaDescription to
     * the {@link FullScreenPlayerActivity}, speeding up the screen rendering
     * while the {@link android.support.v4.media.session.MediaControllerCompat} is connecting.
     */
    public static final String EXTRA_CURRENT_MEDIA_DESCRIPTION =
            "ar.com.lapotoca.resiliencia.CURRENT_MEDIA_DESCRIPTION";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_player);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.materialup_tabs);
        ViewPager viewPager  = (ViewPager) findViewById(R.id.materialup_viewpager);
        AppBarLayout appbarLayout = (AppBarLayout) findViewById(R.id.main_appbar);
        mProfileImage = (ImageView) findViewById(R.id.circle);

        initializeToolbar();

        if(appbarLayout != null) {
            appbarLayout.addOnOffsetChangedListener(this);
        }

        viewPager.setAdapter(new TabsAdapter(getSupportFragmentManager(), this));
        tabLayout.setupWithViewPager(viewPager);

        if (savedInstanceState == null) {
            startFullScreenActivityIfNeeded(getIntent());
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        AnalyticsHelper.getInstance().sendScreen(ACTIVITY_NAME);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        String mediaId = getMediaId();
        if (mediaId != null) {
            outState.putString(SAVED_MEDIA_ID, mediaId);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onMediaItemSelected(MediaBrowserCompat.MediaItem item) {
        getSupportMediaController().getTransportControls()
                .playFromMediaId(item.getMediaId(), null);
    }

    @Override
    public void onMediaItemDownloadSelected(MediaBrowserCompat.MediaItem item) {
        try {
            DownloadMusicManager.getInstance().downloadItem(item);
        } catch (SecurityException e) {
            NotificationHelper.showNotification(this, getString(R.string.download_no_permissions));
        }
    }

    @Override
    public void onMediaItemShared(MediaBrowserCompat.MediaItem item) {
        ShareHelper.getInstance().shareContentOnFacebook(this, item);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        startFullScreenActivityIfNeeded(intent);
    }

    private void startFullScreenActivityIfNeeded(Intent intent) {
        if (intent != null && intent.getBooleanExtra(EXTRA_START_FULLSCREEN, false)) {
            Intent fullScreenIntent = new Intent(this, FullScreenPlayerActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP |
                            Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    .putExtra(EXTRA_CURRENT_MEDIA_DESCRIPTION,
                            intent.getParcelableExtra(EXTRA_CURRENT_MEDIA_DESCRIPTION));
            startActivity(fullScreenIntent);
        }
    }

    public String getMediaId() {
        MediaBrowserFragment fragment = getBrowseFragment();
        if (fragment == null) {
            return null;
        }
        return fragment.getMediaId();
    }

    public MediaBrowserFragment getBrowseFragment() {
        return (MediaBrowserFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
    }

    /*
    Animations
     */

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        if (mMaxScrollSize == 0)
            mMaxScrollSize = appBarLayout.getTotalScrollRange();

        if(mMaxScrollSize == 0)
            return;

        int percentage = (Math.abs(offset)) * 100 / mMaxScrollSize;

        if (percentage >= PERCENTAGE_TO_ANIMATE_AVATAR && mIsAvatarShown) {
            mIsAvatarShown = false;
            mProfileImage.animate().scaleY(0).scaleX(0).setDuration(200).start();
        }

        if (percentage <= PERCENTAGE_TO_ANIMATE_AVATAR && !mIsAvatarShown) {
            mIsAvatarShown = true;

            mProfileImage.animate()
                    .scaleY(1).scaleX(1)
                    .start();
        }
    }


}
