package ar.com.lapotoca.resiliencia.ui.recycler;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ar.com.lapotoca.resiliencia.R;

public class MediaItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    static final int STATE_NONE = 0;
    static final int STATE_PLAYABLE = 1;
    static final int STATE_PAUSED = 2;
    static final int STATE_PLAYING = 3;

    private static ColorStateList sColorStatePlaying;
    private static ColorStateList sColorStateNotPlaying;

    ImageView mImageView;
    ImageView mDownloadView;
    TextView mTitleView;
    TextView mDescriptionView;
    Context mContext;

    OnMediaItemClickListener listener;

    public MediaItemViewHolder(View itemView, OnMediaItemClickListener listener) {
        super(itemView);
        mImageView = (ImageView) itemView.findViewById(R.id.play_eq);
        mTitleView = (TextView) itemView.findViewById(R.id.title);
        mDescriptionView = (TextView) itemView.findViewById(R.id.description);
        mDownloadView = (ImageView) itemView.findViewById(R.id.song_menu_btn);
        this.listener = listener;
        this.itemView.setOnClickListener(this);
        this.mDownloadView.setOnClickListener(this);
    }

    public void setupView(Activity activity, MediaDescriptionCompat description, int state, boolean isLocal) {
        mContext = activity;

        if (sColorStateNotPlaying == null || sColorStatePlaying == null) {
            initializeColorStateLists(activity);
        }

        mTitleView.setText(description.getTitle());
        mDescriptionView.setText(description.getSubtitle());

        if(isLocal) {
            mDownloadView.setVisibility(View.GONE);
        } else {
            mDownloadView.setVisibility(View.VISIBLE);
        }

        updateState(state);
    }

    private void updateState(int state) {
        if (mContext != null) {
            switch (state) {
                case STATE_PLAYABLE:
                    Drawable pauseDrawable = ContextCompat.getDrawable(mContext,
                            R.drawable.ic_play_arrow_black_36dp);
                    DrawableCompat.setTintList(pauseDrawable, sColorStateNotPlaying);
                    mImageView.setImageDrawable(pauseDrawable);
                    mImageView.setVisibility(View.VISIBLE);
                    break;
                case STATE_PLAYING:
                    AnimationDrawable animation = (AnimationDrawable)
                            ContextCompat.getDrawable(mContext, R.drawable.ic_equalizer_white_36dp);
                    DrawableCompat.setTintList(animation, sColorStatePlaying);
                    mImageView.setImageDrawable(animation);
                    mImageView.setVisibility(View.VISIBLE);
                    animation.start();
                    break;
                case STATE_PAUSED:
                    Drawable playDrawable = ContextCompat.getDrawable(mContext,
                            R.drawable.ic_equalizer1_white_36dp);
                    DrawableCompat.setTintList(playDrawable, sColorStatePlaying);
                    mImageView.setImageDrawable(playDrawable);
                    mImageView.setVisibility(View.VISIBLE);
                    break;
                default:
                    mImageView.setVisibility(View.GONE);
            }
        }
    }


    static private void initializeColorStateLists(Context ctx) {
        sColorStateNotPlaying = ColorStateList.valueOf(ctx.getResources().getColor(
                R.color.media_item_icon_not_playing));
        sColorStatePlaying = ColorStateList.valueOf(ctx.getResources().getColor(
                R.color.media_item_icon_playing));
    }

    @Override
    public void onClick(View v) {
        if(listener!= null) {
            if(v.getId() == R.id.song_menu_btn) {
                listener.onMediaItemSettingsClicked(v, getAdapterPosition());
            } else {
                listener.onMediaItemClicked(getAdapterPosition());
            }
        }

    }

    public interface OnMediaItemClickListener {
        void onMediaItemSettingsClicked(View view, int position);
        void onMediaItemClicked(int position);
    }
}