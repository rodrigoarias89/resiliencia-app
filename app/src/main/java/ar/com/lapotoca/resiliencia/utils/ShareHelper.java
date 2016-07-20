package ar.com.lapotoca.resiliencia.utils;

import android.app.Activity;
import android.net.Uri;
import android.support.v4.media.MediaBrowserCompat;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

/**
 * Created by rarias on 7/19/16.
 */
public class ShareHelper {

    public static void shareContentOnFacebook(Activity activity, MediaBrowserCompat.MediaItem item) {
        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("https://developers.facebook.com"))
                .build();
        ShareDialog shareDialog = new ShareDialog(activity);
        shareDialog.show(content);
    }
}
