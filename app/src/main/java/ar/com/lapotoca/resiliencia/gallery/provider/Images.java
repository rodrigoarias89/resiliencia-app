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
            new ImageHolder(true, "images/logo.png"),
            new ImageHolder(true, "images/contratapa.png"),
            new ImageHolder(true, "images/poster.png"),
            new ImageHolder(true, "images/tapa.png"),
    };

    public final static ImageHolder[] imageThumb = new ImageHolder[] {
            new ImageHolder(true, "images_tumblr/logo.png"),
            new ImageHolder(true, "images_tumblr/contratapa.png"),
            new ImageHolder(true, "images_tumblr/poster.png"),
            new ImageHolder(true, "images_tumblr/tapa.png"),

    };
}
