package ar.com.lapotoca.resiliencia.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.media.MediaMetadataCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;

import ar.com.lapotoca.resiliencia.DownloadMusicManager;

/**
 * Utility class to get a list of MusicTrack's based on a server-side JSON
 * configuration.
 */
public class RemoteJSONSource implements MusicProviderSource {

    private Context mContext;

    protected static final String CATALOG_URL =
            "http://storage.googleapis.com/automotive-media/music.json";

    protected static final String HOST_URL = "http://www.fordoogle.com.ar/lapotoca/";

    private static final String JSON_MUSIC = "music";
    private static final String JSON_TITLE = "title";
    private static final String JSON_ALBUM = "album";
    private static final String JSON_ARTIST = "artist";
    private static final String JSON_GENRE = "genre";
    private static final String JSON_SOURCE = "source";
    private static final String JSON_IMAGE = "image";
    private static final String JSON_TRACK_NUMBER = "trackNumber";
    private static final String JSON_TOTAL_TRACK_COUNT = "totalTrackCount";
    private static final String JSON_DURATION = "duration";

    public RemoteJSONSource(Context context) {
        mContext = context;
    }

    @Override
    public Iterator<MediaMetadataCompat> iterator() {
        try {
            String path = HOST_URL;
            JSONObject jsonObj = loadJSONFromAsset();
            ArrayList<MediaMetadataCompat> tracks = new ArrayList<>();
            if (jsonObj != null) {
                JSONArray jsonTracks = jsonObj.getJSONArray(JSON_MUSIC);

                if (jsonTracks != null) {
                    for (int j = 0; j < jsonTracks.length(); j++) {
                        tracks.add(buildFromJSON(jsonTracks.getJSONObject(j), path));
                    }
                }
            }
            return tracks.iterator();
        } catch (JSONException e) {
            throw new RuntimeException("Could not retrieve music list", e);
        }
    }

    private MediaMetadataCompat buildFromJSON(JSONObject json, String basePath) throws JSONException {

        String title = json.getString(JSON_TITLE);
        String album = json.getString(JSON_ALBUM);
        String artist = json.getString(JSON_ARTIST);
        String genre = json.getString(JSON_GENRE);
        String source = json.getString(JSON_SOURCE);
        String remoteSource = json.getString(JSON_SOURCE);
        String iconUrl = json.getString(JSON_IMAGE);
        long isLocal = 0;
        int trackNumber = json.getInt(JSON_TRACK_NUMBER);
        int totalTrackCount = json.getInt(JSON_TOTAL_TRACK_COUNT);
        int duration = json.getInt(JSON_DURATION) * 1000; // ms

        SharedPreferences prefs = mContext.getSharedPreferences(DownloadMusicManager.PREFERENCES_NAME, 0);
        String localURL = prefs.getString(title, null);
        boolean exists = false;
        if(localURL != null) {
            exists = new File(URI.create(localURL).getPath()).exists();
        }

        if(exists) {
            source = localURL;
            isLocal = 1;
        } else {
            source = basePath + source;
        }

        remoteSource = basePath + remoteSource;


        if (!iconUrl.startsWith("http")) {
            iconUrl = basePath + iconUrl;
        }
        // Since we don't have a unique ID in the server, we fake one using the hashcode of
        // the music source. In a real world app, this could come from the server.
        String id = String.valueOf(source.hashCode());

        return new MediaMetadataCompat.Builder()
                .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, id)
                .putString(MusicProviderSource.CUSTOM_METADATA_TRACK_SOURCE, source)
                .putString(MusicProviderSource.CUSTOM_METADATA_TRACK_REMOTE, remoteSource)
                .putString(MediaMetadataCompat.METADATA_KEY_ALBUM, album)
                .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, artist)
                .putLong(MediaMetadataCompat.METADATA_KEY_DURATION, duration)
                .putString(MediaMetadataCompat.METADATA_KEY_GENRE, genre)
                .putString(MediaMetadataCompat.METADATA_KEY_ALBUM_ART_URI, iconUrl)
                .putString(MediaMetadataCompat.METADATA_KEY_TITLE, title)
                .putLong(MediaMetadataCompat.METADATA_KEY_TRACK_NUMBER, trackNumber)
                .putLong(MediaMetadataCompat.METADATA_KEY_NUM_TRACKS, totalTrackCount)
                .putLong(MusicProviderSource.CUSTOM_METADATA_TRACK_LOCAL, isLocal)
                .build();
    }

    private JSONObject loadJSONFromAsset() {
        String json;
        try {
            InputStream is = mContext.getAssets().open("songs.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            return new JSONObject(json);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

}