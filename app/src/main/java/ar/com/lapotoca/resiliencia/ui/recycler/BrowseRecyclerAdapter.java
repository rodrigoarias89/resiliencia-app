package ar.com.lapotoca.resiliencia.ui.recycler;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ar.com.lapotoca.resiliencia.DownloadMusicManager;
import ar.com.lapotoca.resiliencia.R;
import ar.com.lapotoca.resiliencia.utils.MediaIDHelper;

public class BrowseRecyclerAdapter extends RecyclerView.Adapter<MediaItemViewHolder> {

    List<MediaBrowserCompat.MediaItem> items;
    Context mContext;
    MediaItemViewHolder.OnMediaItemClickListener listener;
    DownloadMusicManager downloadMusicManager;

    public BrowseRecyclerAdapter(List<MediaBrowserCompat.MediaItem> items, Context context, MediaItemViewHolder.OnMediaItemClickListener listener) {
        this.items = items;
        this.mContext = context;
        this.listener = listener;
        downloadMusicManager = DownloadMusicManager.getInstance();
    }

    public MediaBrowserCompat.MediaItem getItem(int position) {
        return items.get(position);
    }

    @Override
    public MediaItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.media_list_item, parent, false);
        return new MediaItemViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(MediaItemViewHolder holder, int position) {
        int itemState = getItemState(position);
        MediaBrowserCompat.MediaItem item = items.get(position);
        boolean isLocal = downloadMusicManager.isLocal(item);
        holder.setupView((Activity) mContext, item.getDescription(), itemState, isLocal);
    }

    public int getItemState(int itemPosition) {
        MediaBrowserCompat.MediaItem item = items.get(itemPosition);
        return getItemState(item);
    }

    private int getItemState (MediaBrowserCompat.MediaItem item) {
        int itemState = MediaItemViewHolder.STATE_NONE;
        if (item.isPlayable()) {
            itemState = MediaItemViewHolder.STATE_PLAYABLE;

            MediaControllerCompat controller = ((FragmentActivity)mContext).getSupportMediaController();
            if (controller != null && controller.getMetadata() != null) {
                String currentPlaying = controller.getMetadata().getDescription().getMediaId();
                String musicId = MediaIDHelper.extractMusicIDFromMediaID(
                        item.getDescription().getMediaId());
                if (currentPlaying != null && currentPlaying.equals(musicId)) {
                    PlaybackStateCompat pbState = controller.getPlaybackState();
                    if (pbState == null ||
                            pbState.getState() == PlaybackStateCompat.STATE_ERROR) {
                        itemState = MediaItemViewHolder.STATE_NONE;
                    } else if (pbState.getState() == PlaybackStateCompat.STATE_PLAYING) {
                        itemState = MediaItemViewHolder.STATE_PLAYING;
                    } else {
                        itemState = MediaItemViewHolder.STATE_PAUSED;
                    }
                }
            }
        }

        return itemState;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}
