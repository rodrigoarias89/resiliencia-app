package ar.com.lapotoca.resiliencia.utils;

import android.app.Activity;
import android.net.Uri;
import android.support.v4.media.MediaBrowserCompat;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import ar.com.lapotoca.resiliencia.R;

public class ShareHelper {

    private static String TAG = ShareHelper.class.getName();

    private static ShareHelper instance;
    private CallbackManager callbackManager = CallbackManager.Factory.create();


    public static ShareHelper getInstance() {
        if(instance == null) {
            instance = new ShareHelper();
        }
        return instance;
    }

    public void shareContentOnFacebook(Activity activity, MediaBrowserCompat.MediaItem item) {
        String title = activity.getString(R.string.facebook_title_share);
        String description = String.format(activity.getString(R.string.facebook_description_share_song), item.getDescription().getTitle());
        String url = activity.getString(R.string.facebook_url_share);
        shareContentOnFacebook(activity, title, description, url, item.getDescription().getIconUri());
    }

    public void shareContentOnFacebook(Activity activity) {
        String title = activity.getString(R.string.facebook_title_share);
        String description = (activity.getString(R.string.facebook_description_share_album));
        String url = activity.getString(R.string.facebook_url_share);
        Uri uriImage = Uri.parse(activity.getString(R.string.facebook_image_url_share));
        shareContentOnFacebook(activity, title, description, url, uriImage);
    }

    private void shareContentOnFacebook(Activity activity, String title, String description, String url, Uri imageUri) {
        ShareLinkContent content = new ShareLinkContent.Builder().setContentTitle(title).setContentDescription(description)
                .setContentUrl(Uri.parse(url)).setImageUrl(imageUri)
                .build();
        ShareDialog shareDialog = new ShareDialog(activity);
        shareDialog.show(content);

        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                Log.i(TAG, "onSuccess");
            }

            @Override
            public void onCancel() {
                Log.i(TAG, "onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.e(TAG, "onError", error);
            }
        });
    }

}
