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

package ar.com.lapotoca.resiliencia.gallery.provider;

/**
 * Some simple test data to use for this sample app.
 */
public class Images {

    public final static ImageHolder[] image = new ImageHolder[] {
            new ImageHolder(true, "images/disc_cover.jpg"),
            new ImageHolder(true, "images/potoca_shield.png"),
            new ImageHolder(true, "images/images.jpg"),
            new ImageHolder(true, "images/logo_3.png"),
    };

    public final static ImageHolder[] imageThumb = new ImageHolder[] {
            new ImageHolder(true, "images_tumblr/disc_cover.jpg"),
            new ImageHolder(true, "images_tumblr/potoca_shield.png"),
            new ImageHolder(true, "images_tumblr/images.jpeg"),
            new ImageHolder(true, "images_tumblr/logo_3.png"),

    };
}
