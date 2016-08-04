package ar.com.lapotoca.resiliencia.gallery.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import ar.com.lapotoca.resiliencia.R;
import ar.com.lapotoca.resiliencia.gallery.provider.Images;
import ar.com.lapotoca.resiliencia.gallery.util.ImageFetcher;

/**
 * Created by rarias on 8/4/16.
 */
public class ImagePageAdapter extends RecyclerView.Adapter<ImagePageAdapter.ImagePageVH> {

    private Context mContext;
    private ImageFetcher mImageFetcher;

    public ImagePageAdapter(Context context, ImageFetcher imageFetcher) {
        mContext = context;
        mImageFetcher = imageFetcher;
    }

    @Override
    public ImagePageVH onCreateViewHolder(ViewGroup viewGroup, final int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_image_card, viewGroup, false);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(mContext, ImageDetailActivity.class);
                intent.putExtra(ImageDetailActivity.EXTRA_IMAGE, i);
                mContext.startActivity(intent);
            }
        });
        return new ImagePageVH(itemView);
    }

    @Override
    public void onBindViewHolder(ImagePageVH imagePageVH, int i) {
        imagePageVH.loadImage(i);
    }

    @Override
    public int getItemCount() {
        return Images.imageThumb.length;
    }


    public class ImagePageVH extends RecyclerView.ViewHolder {

        private ImageView mImageView;
        int id;

        public ImagePageVH(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.card_image_view);
        }

        public void loadImage(int i) {
            id = i;
            mImageFetcher.loadImage(Images.imageThumb[i].getUrl(), Images.imageThumb[i].isLocal(), mImageView);
        }
    }
}