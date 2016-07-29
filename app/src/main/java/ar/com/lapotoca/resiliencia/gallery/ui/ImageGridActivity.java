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

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import ar.com.lapotoca.resiliencia.R;
import ar.com.lapotoca.resiliencia.ui.BaseActivity;
import ar.com.lapotoca.resiliencia.ui.MusicPlayerActivity;
import ar.com.lapotoca.resiliencia.utils.AnalyticsHelper;

/**
 * Simple FragmentActivity to hold the main {@link ImageGridFragment} and not much else.
 */
public class ImageGridActivity extends BaseActivity {

    private static final String ACTIVITY_NAME = ImageGridActivity.class.getSimpleName();
    private static final String TAG = "ImageGridActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_art);

        if (getSupportFragmentManager().findFragmentByTag(TAG) == null) {
            final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.art_grid_content, new ImageGridFragment(), TAG);
            ft.commit();
        }

        initializeToolbar();
    }

    @Override
    public void onResume() {
        super.onResume();
        AnalyticsHelper.getInstance().sendScreen(ACTIVITY_NAME);
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(ImageGridActivity.this, MusicPlayerActivity.class);
        startActivity(intent);
    }

}
