package ar.com.lapotoca.resiliencia.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ar.com.lapotoca.resiliencia.DownloadMusicManager;
import ar.com.lapotoca.resiliencia.R;
import ar.com.lapotoca.resiliencia.model.MusicProvider;
import ar.com.lapotoca.resiliencia.ui.recycler.BrowseRecyclerAdapter;
import ar.com.lapotoca.resiliencia.ui.recycler.MediaItemViewHolder;
import ar.com.lapotoca.resiliencia.ui.recycler.SimpleDividerItemDecoration;
import ar.com.lapotoca.resiliencia.utils.NetworkHelper;

/**
 * Created by rarias on 8/2/16.
 */
public class SongsFragment extends Fragment implements MediaItemViewHolder.OnMediaItemClickListener, PopupMenu.OnMenuItemClickListener {

    private RecyclerView recyclerView;
    private Context mContext;
    private BrowseRecyclerAdapter mBrowserAdapter;
    private MediaFragmentListener mMediaFragmentListener;

    private View mErrorView;
    private TextView mErrorMessage;
    private int selectedItem;

    public static Fragment newInstance() {
        return new SongsFragment();
    }

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

        initList();

        return rootView;
    }

    private void initList() {
        List<MediaBrowserCompat.MediaItem> songs = MusicProvider.getInstance().getAllMusic();
        mBrowserAdapter = new BrowseRecyclerAdapter(songs, mContext, SongsFragment.this);
        recyclerView.setAdapter(mBrowserAdapter);
        mBrowserAdapter.notifyDataSetChanged();
    }

    @Override
    public void onMediaItemClicked(int position) {
        MediaBrowserCompat.MediaItem item = mBrowserAdapter.getItem(position);
        boolean isLocal = DownloadMusicManager.getInstance().isLocal(item);
        checkForUserVisibleErrors(false, isLocal);
        mMediaFragmentListener.onMediaItemSelected(item);
    }

    @Override
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

}
