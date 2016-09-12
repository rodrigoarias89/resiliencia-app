package ar.com.lapotoca.resiliencia.utils;

import android.app.Activity;
import android.net.Uri;
import android.support.v4.media.MediaBrowserCompat;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import ar.com.lapotoca.resiliencia.R;

public class ShareHelper {

    private static ShareHelper instance;
    private CallbackManager callbackManager = CallbackManager.Factory.create();

    public static ShareHelper getInstance() {
        if (instance == null) {
            instance = new ShareHelper();
        }
        return instance;
    }

    public void shareContentOnFacebook(Activity activity, MediaBrowserCompat.MediaItem item) {
        String songName = "" + item.getDescription().getTitle();
        this.shareContentOnFacebook(activity, songName);
    }

    public void shareContentOnFacebook(Activity activity, String songName) {
        String title = String.format(activity.getString(R.string.facebook_title_song_share), songName);
        String description = activity.getString(R.string.facebook_description_share_song);
        String url = activity.getString(R.string.facebook_url_share);
        Uri uriImage = Uri.parse(activity.getString(R.string.facebook_image_url_share));
        shareContentOnFacebook(activity, title, description, url, uriImage);

        AnalyticsHelper.getInstance().sendSongShareEvent(songName);
    }

    public void shareContentOnFacebook(Activity activity) {
        String title = activity.getString(R.string.facebook_title_share);
        String description = (activity.getString(R.string.facebook_description_share_album));
        String url = activity.getString(R.string.facebook_url_share);
        Uri uriImage = Uri.parse(activity.getString(R.string.facebook_image_url_share));
        shareContentOnFacebook(activity, title, description, url, uriImage);

        AnalyticsHelper.getInstance().sendAppShareEvent();
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
                AnalyticsHelper.getInstance().sendAppShareCompleted();
            }

            @Override
            public void onCancel() {
                AnalyticsHelper.getInstance().sendAppShareCanceled();
            }

            @Override
            public void onError(FacebookException error) {
                AnalyticsHelper.getInstance().sendAppShareFailed(error.getMessage());
            }
        });
    }

}
