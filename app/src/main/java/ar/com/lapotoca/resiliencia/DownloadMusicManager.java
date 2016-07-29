package ar.com.lapotoca.resiliencia;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v7.app.AlertDialog;

import java.util.ArrayList;
import java.util.List;

import ar.com.lapotoca.resiliencia.model.MusicProvider;
import ar.com.lapotoca.resiliencia.model.MusicProviderSource;
import ar.com.lapotoca.resiliencia.utils.AnalyticsHelper;
import ar.com.lapotoca.resiliencia.utils.MediaIDHelper;
import ar.com.lapotoca.resiliencia.utils.NotificationHelper;


public class DownloadMusicManager {

    private static DownloadMusicManager instance;

    public static final String PREFERENCES_NAME = "la.potoca.resiliencia.guardados";

    private Context mContext;
    private MusicProvider mMusicProvider;
    private DownloadManager dm;
    private List<Integer> mSelectedItems;


    private long enqueue;

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {

                DownloadManager.Query query = new DownloadManager.Query();
                query.setFilterById(enqueue);
                Cursor c = dm.query(query);

                if (c.moveToFirst()) {

                    int columnIndex = c
                            .getColumnIndex(DownloadManager.COLUMN_STATUS);
                    if (DownloadManager.STATUS_SUCCESSFUL == c
                            .getInt(columnIndex)) {

                        String title = c.getString(c.getColumnIndex(DownloadManager.COLUMN_TITLE));

                        String uriString = c
                                .getString(c
                                        .getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));

                        SharedPreferences prefs = mContext.getSharedPreferences(PREFERENCES_NAME, 0);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString(title, uriString);
                        editor.commit();

                        MediaMetadataCompat track = mMusicProvider.getMusicByTitle(title);
                        MediaMetadataCompat newTrack = new MediaMetadataCompat.Builder(track).putString(MusicProviderSource.CUSTOM_METADATA_TRACK_SOURCE, uriString).putLong(MusicProviderSource.CUSTOM_METADATA_TRACK_LOCAL, 1).build();

                        mMusicProvider.changeMetadataForNewOne(track, newTrack);
                    }
                }

            }

        }
    };

    private DownloadMusicManager(Context context, MusicProvider musicProvider) {
        this.mContext = context;
        this.mMusicProvider = musicProvider;
        dm = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
        mContext.registerReceiver(broadcastReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    public static void createInstance(Context context, MusicProvider musicProvider) {
        if (instance == null) {
            instance = new DownloadMusicManager(context, musicProvider);
        }
    }

    public static void destroy() {
        if (instance != null) {
            instance.unregisterReceiver();
            instance = null;
        }
    }

    public void unregisterReceiver() {
        if (mContext != null) {
            mContext.unregisterReceiver(broadcastReceiver);
        }
    }

    public static synchronized DownloadMusicManager getInstance() {
        return instance;
    }

    public boolean isLocal(MediaBrowserCompat.MediaItem item) {
        MediaMetadataCompat track = mMusicProvider.getMusic(
                MediaIDHelper.extractMusicIDFromMediaID(item.getDescription().getMediaId()));
        return isLocal(track);
    }

    public boolean isLocal(MediaMetadataCompat track) {
        long isLocal = track.getLong(MusicProviderSource.CUSTOM_METADATA_TRACK_LOCAL);
        return isLocal != 0;
    }

    public void downloadItem(MediaBrowserCompat.MediaItem item) {
        MediaMetadataCompat track = mMusicProvider.getMusic(
                MediaIDHelper.extractMusicIDFromMediaID(item.getDescription().getMediaId()));
        String songName = "" + item.getDescription().getTitle();
        addDownloadToQueue(track);
        showDownloadNotification(songName);
    }

    private void addDownloadToQueue(MediaMetadataCompat track) {
        String songName = "" + track.getDescription().getTitle();
        String source = track.getString(MusicProviderSource.CUSTOM_METADATA_TRACK_SOURCE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(source));
        String fileName = songName + ".mp3";
        request.setDescription(mContext.getString(R.string.downloading));
        request.setTitle(songName);
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_MUSIC, fileName);
        enqueue = dm.enqueue(request);

        AnalyticsHelper.getInstance().sendDownloadSong(songName);
    }

    public void downloadAll(Context context) {
        final List<MediaMetadataCompat> notLocal = new ArrayList<>();

        for (MediaMetadataCompat media : mMusicProvider.getMusicList()) {
            if (!isLocal(media)) {
                notLocal.add(media);
            }
        }

        if (notLocal.isEmpty()) {
            showNotification(context.getString(R.string.download_all_empty));
            return;
        }

        showDownloadAllDialog(context, notLocal, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (mSelectedItems == null || mSelectedItems.size() == 0) {
                    showNotification(mContext.getString(R.string.download_all_no_song_selected));
                    return;
                }
                for (Integer index : mSelectedItems) {
                    try {
                        addDownloadToQueue(notLocal.get(index));
                    } catch (SecurityException e) {
                        showNotification(mContext.getString(R.string.download_no_permissions));
                        return;
                    }
                }
                showNotification(String.format(mContext.getString(R.string.download_all_msg), mSelectedItems.size()));
            }
        });
    }

    private void showDownloadNotification(String title) {
        showNotification(String.format(mContext.getString(R.string.download_msg), title));
    }

    private void showNotification(String notification) {
        NotificationHelper.showNotification(mContext, notification);
    }

    private void showDownloadAllDialog(Context context, List<MediaMetadataCompat> songs, DialogInterface.OnClickListener listener) {

        mSelectedItems = new ArrayList();

        CharSequence[] songNames = new CharSequence[songs.size()];
        boolean[] defaults = new boolean[songs.size()];
        for (int i = 0; i < songs.size(); i++) {
            songNames[i] = songs.get(i).getDescription().getTitle();
            defaults[i] = true;
            mSelectedItems.add(i);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(mContext.getString(R.string.download_all_alert_title));

        builder.setMultiChoiceItems(songNames, defaults, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which,
                                boolean isChecked) {
                if (isChecked) {
                    mSelectedItems.add(which);
                } else if (mSelectedItems.contains(which)) {
                    mSelectedItems.remove(Integer.valueOf(which));
                }

            }
        });

        builder.setPositiveButton(R.string.download_all_alert_ok, listener);
        builder.setNegativeButton(R.string.download_all_alert_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // nothing to do
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}