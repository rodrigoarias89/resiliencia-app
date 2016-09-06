package ar.com.lapotoca.resiliencia.utils;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import ar.com.lapotoca.resiliencia.ResilienciaApplication;

/**
 * Created by rarias on 7/29/16.
 */
public class AnalyticsHelper {

    private static AnalyticsHelper instance;

    private final static String SHARE_CATEGORY = "Compartir";
    private final static String DOWNLOAD_CATEGORY = "Descargar";
    private final static String PLAYBACK_CATEGORY = "Reproduccion";

    private final static String SHARE_APP_ACTION = "Compartir App";
    private final static String SHARE_SONG_ACTION = "Compartir Cancion";
    private final static String SHARE_IMAGE_ACTION = "Compartir Imagen";
    private final static String DOWNLOAD_SONG_ACTION = "Descargar Cancion";
    private final static String PLAYBACK_PLAY_ACTION = "Reproducir";
    private final static String PLAYBACK_ERROR = "Error";

    private final static String SHARE_APP_SUCCESS = "Completado";
    private final static String SHARE_APP_CANCEL = "Cancelado";
    private final static String SHARE_APP_ERROR = "Fallo";

    private final static String SHARE_IMAGE_SUCCESS = "IMG Completado";
    private final static String SHARE_IMAGE_CANCEL = "IMG Cancelado";
    private final static String SHARE_IMAGE_ERROR = "IMG Fallo";


    private Tracker mTracker;

    private AnalyticsHelper(ResilienciaApplication app) {
        mTracker = app.getDefaultTracker();
    }

    public static void createInstance(ResilienciaApplication app) {
        if(instance == null) {
            instance = new AnalyticsHelper(app);
        }
    }

    public static AnalyticsHelper getInstance() {
        return instance;
    }

    public void sendScreen(String screenName) {
        mTracker.setScreenName(screenName);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    public void sendAppShareEvent() {
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory(SHARE_CATEGORY)
                .setAction(SHARE_APP_ACTION)
                .build());
    }

    public void sendSongShareEvent(String songName) {
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory(SHARE_CATEGORY)
                .setAction(SHARE_SONG_ACTION)
                .setLabel(songName)
                .build());
    }

    public void sendAppShareCompleted() {
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory(SHARE_CATEGORY)
                .setAction(SHARE_APP_SUCCESS)
                .build());
    }

    public void sendAppShareCanceled() {
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory(SHARE_CATEGORY)
                .setAction(SHARE_APP_CANCEL)
                .build());
    }

    public void sendAppShareFailed(String exceptionMessage) {
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory(SHARE_CATEGORY)
                .setAction(SHARE_APP_ERROR)
                .setLabel(exceptionMessage)
                .build());
    }

    public void sendImageShareEvent(String imageName) {
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory(SHARE_CATEGORY)
                .setAction(SHARE_IMAGE_ACTION)
                .setLabel(imageName)
                .build());
    }

    public void sendImageShareCompleted() {
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory(SHARE_CATEGORY)
                .setAction(SHARE_IMAGE_SUCCESS)
                .build());
    }

    public void sendImageShareCanceled() {
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory(SHARE_CATEGORY)
                .setAction(SHARE_IMAGE_CANCEL)
                .build());
    }

    public void sendImageShareFailed(String exceptionMessage) {
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory(SHARE_CATEGORY)
                .setAction(SHARE_IMAGE_ERROR)
                .setLabel(exceptionMessage)
                .build());
    }

    public void sendPlaybackError(String exceptionMessage) {
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory(PLAYBACK_CATEGORY)
                .setAction(PLAYBACK_ERROR)
                .setLabel(exceptionMessage)
                .build());
    }

    public void sendDownloadSong(String songName) {
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory(DOWNLOAD_CATEGORY)
                .setAction(DOWNLOAD_SONG_ACTION)
                .setLabel(songName)
                .build());
    }

    public void sendPlaySong(String songName) {
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory(PLAYBACK_CATEGORY)
                .setAction(PLAYBACK_PLAY_ACTION)
                .setLabel(songName)
                .build());
    }

}
