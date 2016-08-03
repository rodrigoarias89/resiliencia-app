package ar.com.lapotoca.resiliencia.ui;

import android.support.v4.media.MediaBrowserCompat;

/**
 * Created by rarias on 8/3/16.
 */
public interface MediaFragmentListener extends MediaBrowserProvider {

    void onMediaItemSelected(MediaBrowserCompat.MediaItem item);

    void onMediaItemDownloadSelected(MediaBrowserCompat.MediaItem item);

    void onMediaItemShared(MediaBrowserCompat.MediaItem item);

}
