package ar.com.lapotoca.resiliencia.utils;

import android.app.Activity;
import android.net.Uri;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

/**
 * Created by rarias on 7/19/16.
 */
public class ShareHelper {

    public void shareContentOnFacebook(Activity activity) {
        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("https://developers.facebook.com"))
                .build();
        ShareDialog shareDialog = new ShareDialog(activity);
        shareDialog.show(content);
    }
}
