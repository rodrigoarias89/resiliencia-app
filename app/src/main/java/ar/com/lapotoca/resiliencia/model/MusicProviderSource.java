package ar.com.lapotoca.resiliencia.model;

import android.support.v4.media.MediaMetadataCompat;

import java.util.Iterator;

public interface MusicProviderSource {
    String CUSTOM_METADATA_TRACK_SOURCE = "__SOURCE__";
    String CUSTOM_METADATA_TRACK_LOCAL = "__ISLOCAL__";
    String CUSTOM_METADATA_TRACK_REMOTE = "__REMOTE__";
    Iterator<MediaMetadataCompat> iterator();
}

