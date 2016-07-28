package ar.com.lapotoca.resiliencia.ui;

import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ar.com.lapotoca.resiliencia.DownloadMusicManager;
import ar.com.lapotoca.resiliencia.R;
import ar.com.lapotoca.resiliencia.ui.recycler.BrowseRecyclerAdapter;
import ar.com.lapotoca.resiliencia.ui.recycler.MediaItemViewHolder;
import ar.com.lapotoca.resiliencia.ui.recycler.SimpleDividerItemDecoration;
import ar.com.lapotoca.resiliencia.utils.LogHelper;
import ar.com.lapotoca.resiliencia.utils.MediaIDHelper;
import ar.com.lapotoca.resiliencia.utils.NetworkHelper;

/**
 * A Fragment that lists all the various browsable queues available
 * from a {@link android.service.media.MediaBrowserService}.
 * <p/>
 * It uses a {@link MediaBrowserCompat} to connect to the {@link ar.com.lapotoca.resiliencia.MusicService}.
 * Once connected, the fragment subscribes to get all the children.
 * All {@link MediaBrowserCompat.MediaItem}'s that can be browsed are shown in a ListView.
 */
public class MediaBrowserFragment extends Fragment implements MediaItemViewHolder.OnMediaItemClickListener, PopupMenu.OnMenuItemClickListener {

    private static final String TAG = LogHelper.makeLogTag(MediaBrowserFragment.class);

    private static final String ARG_MEDIA_ID = "media_id";

    private BrowseRecyclerAdapter mBrowserAdapter;
    private Context mContext;
    private RecyclerView recyclerView;

    private String mMediaId;
    private MediaFragmentListener mMediaFragmentListener;
    private View mErrorView;
    private TextView mErrorMessage;

    private int selectedItem;

    private final BroadcastReceiver mConnectivityChangeReceiver = new BroadcastReceiver() {
        private boolean oldOnline = false;

        @Override
        public void onReceive(Context context, Intent intent) {
            // We don't care about network changes while this fragment is not associated
            // with a media ID (for example, while it is being initialized)
            if (mMediaId != null) {
                boolean isOnline = NetworkHelper.isOnline(context);
                if (isOnline != oldOnline) {
                    oldOnline = isOnline;
                    checkForUserVisibleErrors(false);
                    if (isOnline) {
                        mBrowserAdapter.notifyDataSetChanged();
                    }
                }
            }
        }
    };

    // Receive callbacks from the MediaController. Here we update our state such as which queue
    // is being shown, the current title and description and the PlaybackState.
    private final MediaControllerCompat.Callback mMediaControllerCallback =
            new MediaControllerCompat.Callback() {
                @Override
                public void onMetadataChanged(MediaMetadataCompat metadata) {
                    super.onMetadataChanged(metadata);
                    if (metadata == null) {
                        return;
                    }
                    LogHelper.d(TAG, "Received metadata change to media ",
                            metadata.getDescription().getMediaId());
                    mBrowserAdapter.notifyDataSetChanged();
                }

                @Override
                public void onPlaybackStateChanged(@NonNull PlaybackStateCompat state) {
                    super.onPlaybackStateChanged(state);
                    LogHelper.d(TAG, "Received state change: ", state);
                    switch (state.getState()) {
                        case PlaybackStateCompat.STATE_ERROR:
                        case PlaybackStateCompat.STATE_CONNECTING:
                        case PlaybackStateCompat.STATE_NONE:
                            checkForUserVisibleErrors(false);
                        default:
                            break;
                    }
                    mBrowserAdapter.notifyDataSetChanged();
                }
            };

    private final MediaBrowserCompat.SubscriptionCallback mSubscriptionCallback =
            new MediaBrowserCompat.SubscriptionCallback() {
                @Override
                public void onChildrenLoaded(@NonNull String parentId,
                                             @NonNull List<MediaBrowserCompat.MediaItem> children) {
                    try {
                        LogHelper.d(TAG, "fragment onChildrenLoaded, parentId=" + parentId +
                                "  count=" + children.size());

                        mBrowserAdapter = new BrowseRecyclerAdapter(children, mContext, MediaBrowserFragment.this);
                        recyclerView.setAdapter(mBrowserAdapter);
                        mBrowserAdapter.notifyDataSetChanged();
                    } catch (Throwable t) {
                        LogHelper.e(TAG, "Error on childrenloaded", t);
                    }
                }

                @Override
                public void onError(@NonNull String id) {
                    LogHelper.e(TAG, "browse fragment subscription onError, id=" + id);
                    Toast.makeText(getActivity(), R.string.error_loading_media, Toast.LENGTH_LONG).show();
                    checkForUserVisibleErrors(true);
                }
            };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mMediaFragmentListener = (MediaFragmentListener) context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            mMediaFragmentListener = (MediaFragmentListener) activity;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        mErrorView = rootView.findViewById(R.id.playback_error);
        mErrorMessage = (TextView) mErrorView.findViewById(R.id.error_message);

        this.mContext = container.getContext();
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_list);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(mContext));
        recyclerView.setAdapter(mBrowserAdapter);

        //restart selectedItem
        selectedItem = -1;

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        // fetch browsing information to fill the listview:
        if (mMediaFragmentListener != null) {
            MediaBrowserCompat mediaBrowser = mMediaFragmentListener.getMediaBrowser();

            LogHelper.d(TAG, "fragment.onStart, mediaId=", mMediaId,
                    "  onConnected=" + mediaBrowser.isConnected());

            if (mediaBrowser.isConnected()) {
                onConnected();
            }
        }

        // Registers BroadcastReceiver to track network connection changes.
        this.getActivity().registerReceiver(mConnectivityChangeReceiver,
                new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    public void onStop() {
        super.onStop();
        MediaBrowserCompat mediaBrowser = mMediaFragmentListener.getMediaBrowser();
        if (mediaBrowser != null && mediaBrowser.isConnected() && mMediaId != null) {
            mediaBrowser.unsubscribe(mMediaId);
        }
        MediaControllerCompat controller = ((FragmentActivity) getActivity())
                .getSupportMediaController();
        if (controller != null) {
            controller.unregisterCallback(mMediaControllerCallback);
        }
        this.getActivity().unregisterReceiver(mConnectivityChangeReceiver);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mMediaFragmentListener = null;
    }

    public String getMediaId() {
        Bundle args = getArguments();
        if (args != null) {
            return args.getString(ARG_MEDIA_ID);
        }
        return null;
    }

    public void setMediaId(String mediaId) {
        Bundle args = new Bundle(1);
        args.putString(MediaBrowserFragment.ARG_MEDIA_ID, mediaId);
        setArguments(args);
    }

    // Called when the MediaBrowser is connected. This method is either called by the
    // fragment.onStart() or explicitly by the activity in the case where the connection
    // completes after the onStart()
    public void onConnected() {
        if (isDetached()) {
            return;
        }
        mMediaId = getMediaId();
        if (mMediaId == null) {
            mMediaId = mMediaFragmentListener.getMediaBrowser().getRoot();
        }
        updateTitle();

        // Unsubscribing before subscribing is required if this mediaId already has a subscriber
        // on this MediaBrowser instance. Subscribing to an already subscribed mediaId will replace
        // the callback, but won't trigger the initial callback.onChildrenLoaded.
        //
        // This is temporary: A bug is being fixed that will make subscribe
        // consistently call onChildrenLoaded initially, no matter if it is replacing an existing
        // subscriber or not. Currently this only happens if the mediaID has no previous
        // subscriber or if the media content changes on the service side, so we need to
        // unsubscribe first.
        mMediaFragmentListener.getMediaBrowser().unsubscribe(mMediaId);

        mMediaFragmentListener.getMediaBrowser().subscribe(mMediaId, mSubscriptionCallback);

        // Add MediaController callback so we can redraw the list when metadata changes:
        MediaControllerCompat controller = ((FragmentActivity) getActivity())
                .getSupportMediaController();
        if (controller != null) {
            controller.registerCallback(mMediaControllerCallback);
        }
    }

    private void checkForUserVisibleErrors(boolean forceError) {
        checkForUserVisibleErrors(forceError, false);
    }

    private void checkForUserVisibleErrors(boolean forceError, boolean isLocal) {
        boolean showError = forceError;
        // If offline, message is about the lack of connectivity:
        if (!isLocal && !NetworkHelper.isOnline(getActivity())) {
            mErrorMessage.setText(R.string.error_no_connection);
            showError = true;
        } else {
            // otherwise, if state is ERROR and metadata!=null, use playback state error message:
            MediaControllerCompat controller = ((FragmentActivity) getActivity())
                    .getSupportMediaController();
            if (controller != null
                    && controller.getMetadata() != null
                    && controller.getPlaybackState() != null
                    && controller.getPlaybackState().getState() == PlaybackStateCompat.STATE_ERROR
                    && controller.getPlaybackState().getErrorMessage() != null) {
                mErrorMessage.setText(controller.getPlaybackState().getErrorMessage());
                showError = true;
            } else if (forceError) {
                // Finally, if the caller requested to show error, show a generic message:
                mErrorMessage.setText(R.string.error_loading_media);
                showError = true;
            }
        }
        mErrorView.setVisibility(showError ? View.VISIBLE : View.GONE);
    }

    private void updateTitle() {
        if (MediaIDHelper.MEDIA_ID_ROOT.equals(mMediaId)) {
            mMediaFragmentListener.setToolbarTitle(null);
            return;
        }

        MediaBrowserCompat mediaBrowser = mMediaFragmentListener.getMediaBrowser();
        mediaBrowser.getItem(mMediaId, new MediaBrowserCompat.ItemCallback() {
            @Override
            public void onItemLoaded(MediaBrowserCompat.MediaItem item) {
                mMediaFragmentListener.setToolbarTitle(
                        item.getDescription().getTitle());
            }
        });
    }

    @Override
    public void onMediaItemClicked(int position) {
        Log.i(TAG, "item clicked: " + position);
        MediaBrowserCompat.MediaItem item = mBrowserAdapter.getItem(position);
        boolean isLocal = DownloadMusicManager.getInstance().isLocal(item);
        checkForUserVisibleErrors(false, isLocal);
        mMediaFragmentListener.onMediaItemSelected(item);
    }

    public void onMediaItemSettingsClicked(View view, int position) {
        selectedItem = position;
        PopupMenu popup = new PopupMenu(this.getActivity(), view);
        popup.setOnMenuItemClickListener(this);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.song_menu, popup.getMenu());

        MediaBrowserCompat.MediaItem item = mBrowserAdapter.getItem(position);
        DownloadMusicManager downloadMusicManager = DownloadMusicManager.getInstance();
        if (downloadMusicManager != null) {
            if (DownloadMusicManager.getInstance().isLocal(item)) {
                popup.getMenu().findItem(R.id.action_settings_descargar).setEnabled(false);
            }
        }

        popup.show();
    }

    public boolean onMenuItemClick(MenuItem item) {
        if (selectedItem < 0) {
            return false;
        }

        MediaBrowserCompat.MediaItem mediaItem = mBrowserAdapter.getItem(selectedItem);
        selectedItem = -1;

        switch (item.getItemId()) {
            case R.id.action_settings_descargar:
                mMediaFragmentListener.onMediaItemDownloadSelected(mediaItem);
                return true;
            case R.id.action_settings_compartir:
                mMediaFragmentListener.onMediaItemShared(mediaItem);
                return true;
            default:
                return false;
        }
    }

    public interface MediaFragmentListener extends MediaBrowserProvider {

        void onMediaItemSelected(MediaBrowserCompat.MediaItem item);

        void onMediaItemDownloadSelected(MediaBrowserCompat.MediaItem item);

        void onMediaItemShared(MediaBrowserCompat.MediaItem item);

        void setToolbarTitle(CharSequence title);
    }

}