package ar.com.lapotoca.resiliencia.gallery.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ar.com.lapotoca.resiliencia.R;
import ar.com.lapotoca.resiliencia.gallery.util.ImageCache;
import ar.com.lapotoca.resiliencia.gallery.util.ImageFetcher;

/**
 * Created by rarias on 8/3/16.
 */
public class ImageListFragment extends Fragment {

    private static final String IMAGE_CACHE_DIR = "thumbs";

    private RecyclerView mRecyclerView;
    private ImageFetcher mImageFetcher;

    private int mImageThumbSize;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mImageThumbSize = getResources().getDimensionPixelSize(R.dimen.image_thumbnail_size);

        ImageCache.ImageCacheParams cacheParams =
                new ImageCache.ImageCacheParams(getActivity(), IMAGE_CACHE_DIR);

        cacheParams.setMemCacheSizePercent(0.25f); // Set memory cache to 25% of app memory

        // The ImageFetcher takes care of loading images into our ImageView children asynchronously
        mImageFetcher = new ImageFetcher(getActivity(), mImageThumbSize);
        mImageFetcher.setLoadingImage(R.drawable.empty_photo);
        mImageFetcher.addImageCache(getActivity().getSupportFragmentManager(), cacheParams);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.image_page_fragment, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        return rootView;
    }

    @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRecyclerView();
    }

    private void initRecyclerView() {
        mRecyclerView.setAdapter(new ImagePageAdapter(getActivity(), mImageFetcher));
    }

    public static Fragment newInstance() {
        return new ImageListFragment();
    }


}
