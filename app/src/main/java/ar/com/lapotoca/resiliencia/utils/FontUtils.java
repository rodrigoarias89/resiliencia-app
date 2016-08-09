package ar.com.lapotoca.resiliencia.utils;

import android.graphics.Typeface;

import ar.com.lapotoca.resiliencia.ResilienciaApplication;

/**
 * Created by rarias on 8/9/16.
 */
public class FontUtils {

    private static FontUtils INSTANCE;
    private Typeface sardonyxTypeface;

    private FontUtils(ResilienciaApplication app) {
        sardonyxTypeface = Typeface.createFromAsset(app.getAssets(), "fonts/sardonyx-regular.ttf");
    }

    public static void init(ResilienciaApplication app) {
        INSTANCE = new FontUtils(app);
    }

    public Typeface getSardonyxTypeface() {
        return sardonyxTypeface;
    }

}
