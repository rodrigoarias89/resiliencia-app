/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ar.com.lapotoca.resiliencia.gallery.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import ar.com.lapotoca.resiliencia.R;
import ar.com.lapotoca.resiliencia.gallery.provider.AssetProvider;
import ar.com.lapotoca.resiliencia.gallery.provider.ImageHolder;
import ar.com.lapotoca.resiliencia.gallery.provider.Images;
import ar.com.lapotoca.resiliencia.gallery.util.ImageCache;
import ar.com.lapotoca.resiliencia.gallery.util.ImageFetcher;
import ar.com.lapotoca.resiliencia.ui.ActionBarCastActivity;
import ar.com.lapotoca.resiliencia.utils.AnalyticsHelper;
import ar.com.lapotoca.resiliencia.utils.NotificationHelper;

public class ImageDetailActivity extends ActionBarCastActivity {

    private static final String ACTIVITY_NAME = ImageDetailActivity.class.getSimpleName();

    private static final String IMAGE_CACHE_DIR = "images";
    public static final String EXTRA_IMAGE = "extra_image";
    private static final int BUFFER_LENGHT = 1024;

    private ImagePagerAdapter mAdapter;
    private ImageFetcher mImageFetcher;
    private ViewPager mPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.image_detail_pager);

        // Fetch screen height and width, to use as our max size when loading images as this
        // activity runs full screen
        final DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        final int height = displayMetrics.heightPixels;
        final int width = displayMetrics.widthPixels;

        // For this sample we'll use half of the longest width to resize our images. As the
        // image scaling ensures the image is larger than this, we should be left with a
        // resolution that is appropriate for both portrait and landscape. For best image quality
        // we shouldn't divide by 2, but this will use more memory and require a larger memory
        // cache.
        final int longest = (height > width ? height : width) / 2;

        ImageCache.ImageCacheParams cacheParams =
                new ImageCache.ImageCacheParams(this, IMAGE_CACHE_DIR);
        cacheParams.setMemCacheSizePercent(0.25f); // Set memory cache to 25% of app memory

        // The ImageFetcher takes care of loading images into our ImageView children asynchronously
        mImageFetcher = new ImageFetcher(this, longest);
        mImageFetcher.addImageCache(getSupportFragmentManager(), cacheParams);
        mImageFetcher.setImageFadeIn(false);

        // Set up ViewPager and backing adapter
        mAdapter = new ImagePagerAdapter(getSupportFragmentManager(), Images.image.length);
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);
        mPager.setPageMargin((int) getResources().getDimension(R.dimen.horizontal_page_margin));
        mPager.setOffscreenPageLimit(2);

        // Set up activity to go full screen
        getWindow().addFlags(LayoutParams.FLAG_FULLSCREEN);

        initializeToolbar();
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Set the current item based on the extra passed in to this activity
        final int extraCurrentItem = getIntent().getIntExtra(EXTRA_IMAGE, -1);
        if (extraCurrentItem != -1) {
            mPager.setCurrentItem(extraCurrentItem);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        AnalyticsHelper.getInstance().sendScreen(ACTIVITY_NAME);
        mImageFetcher.setExitTasksEarly(false);
    }

    @Override
    public void onPause() {
        super.onPause();
        mImageFetcher.setExitTasksEarly(true);
        mImageFetcher.flushCache();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mImageFetcher.closeCache();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.image_menu, menu);
        MenuItem shareItem = menu.findItem(R.id.menu_share);
        shareItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                try {

                    ImageHolder img = Images.image[mPager.getCurrentItem()];
                    if (img == null) {
                        return false;
                    }

                    AnalyticsHelper.getInstance().sendImageShareEvent(img.getUrl());

                    Uri bmpUri;
                    if (img.isLocal()) {
                        bmpUri = Uri.parse("content://" + AssetProvider.CONTENT_URI + "/" + img.getUrl());
                    } else {
                        ImageView iv = (ImageView) findViewById(R.id.picImageView);
                        bmpUri = getLocalBitmapUri(iv);
                    }
                    if (bmpUri != null) {
                        Intent shareIntent = new Intent();
                        shareIntent.setAction(Intent.ACTION_SEND);
                        shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
                        shareIntent.setType("image/*");
                        startActivity(Intent.createChooser(shareIntent, getString(R.string.share_item)));

                        AnalyticsHelper.getInstance().sendImageShareCompleted();
                        return true;
                    } else {

                        AnalyticsHelper.getInstance().sendImageShareCanceled();
                        return false;
                    }
                } catch (Exception e) {
                    AnalyticsHelper.getInstance().sendImageShareFailed(e.getMessage());
                    return false;
                }
            }
        });

        MenuItem downloadItem = menu.findItem(R.id.download_asset);
        downloadItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Context context = ImageDetailActivity.this;

                String appDirectoryName = context.getString(R.string.app_name);
                File imageRoot = new File(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES), appDirectoryName);

                ImageHolder img = Images.image[mPager.getCurrentItem()];
                if (img == null) {
                    return false;
                }

                AssetManager assetManager = context.getAssets();
                try {
                    InputStream is = assetManager.open(img.getUrl());
                    String fileName = img.getUrl().split("/")[1];

                    imageRoot.mkdirs();
                    File image = new File(imageRoot, fileName);

                    byte[] buffer = new byte[BUFFER_LENGHT];
                    FileOutputStream fos = new FileOutputStream(image);
                    int read = 0;

                    while ((read = is.read(buffer, 0, 1024)) >= 0) {
                        fos.write(buffer, 0, read);
                    }

                    fos.flush();
                    fos.close();
                    is.close();

                    String [] paths = {image.getAbsolutePath()};

                    MediaScannerConnection.scanFile(context, paths, null, null);
                    NotificationHelper.showNotification(context, context.getString(R.string.download_image_succesfull));


                } catch (Exception e) {
                    NotificationHelper.showNotification(context, context.getString(R.string.download_no_permissions));
                }

                return true;
            }
        });

        return true;
    }

    // Returns the URI path to the Bitmap displayed in specified ImageView
    private Uri getLocalBitmapUri(ImageView imageView) {
        // Extract Bitmap from ImageView drawable
        Drawable drawable = imageView.getDrawable();
        Bitmap bmp;
        if (drawable instanceof BitmapDrawable) {
            bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        } else {
            return null;
        }
        if (bmp == null) {
            return null;
        }
        // Store image to default external storage directory
        Uri bmpUri = null;
        try {
            // Use methods on Context to access package-specific directories on external storage.
            // This way, you don't need to request external read/write permission.
            // See https://youtu.be/5xVh-7ywKpE?t=25m25s
            File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }

    /**
     * Called by the ViewPager child fragments to load images via the one ImageFetcher
     */
    public ImageFetcher getImageFetcher() {
        return mImageFetcher;
    }

    /**
     * The main adapter that backs the ViewPager. A subclass of FragmentStatePagerAdapter as there
     * could be a large number of items in the ViewPager and we don't want to retain them all in
     * memory at once but create/destroy them on the fly.
     */
    private class ImagePagerAdapter extends FragmentStatePagerAdapter {
        private final int mSize;

        public ImagePagerAdapter(FragmentManager fm, int size) {
            super(fm);
            mSize = size;
        }

        @Override
        public int getCount() {
            return mSize;
        }

        @Override
        public Fragment getItem(int position) {
            return ImageDetailFragment.newInstance(Images.image[position]);
        }
    }


}
