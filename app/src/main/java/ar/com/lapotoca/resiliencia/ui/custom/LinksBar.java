package ar.com.lapotoca.resiliencia.ui.custom;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import ar.com.lapotoca.resiliencia.R;


public class LinksBar extends LinearLayout {

    private Context mContext;
    private ImageView btnFacebook;
    private ImageView btnTwitter;
    private ImageView btnInstagram;
    private ImageView btnChrome;
    private ImageView btnSpotify;
    private ImageView btnYoutube;

    private int mIconPadding;


    public LinksBar (Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.LinksBar, 0, 0);
        mIconPadding = a.getInteger(R.styleable.LinksBar_iconPadding, 0);
        init();
    }

    private void init(){
        inflate(getContext(), R.layout.links_bar, this);
        btnFacebook = (ImageView) findViewById(R.id.btnFacebook);
        btnTwitter = (ImageView) findViewById(R.id.btnTwitter);
        btnInstagram = (ImageView) findViewById(R.id.btnInstagram);
        btnChrome = (ImageView) findViewById(R.id.btnChrome);
        btnSpotify = (ImageView) findViewById(R.id.btnSpotify);
        btnYoutube = (ImageView) findViewById(R.id.btnYoutube);

        btnFacebook.setPadding(mIconPadding, 0, mIconPadding, 0);
        btnTwitter.setPadding(mIconPadding, 0, mIconPadding, 0);
        btnInstagram.setPadding(mIconPadding, 0, mIconPadding, 0);
        btnChrome.setPadding(mIconPadding, 0, mIconPadding, 0);
        btnSpotify.setPadding(mIconPadding, 0, mIconPadding, 0);
        btnYoutube.setPadding(mIconPadding, 0, mIconPadding, 0);

        addListeners();
    }

    private void addListeners() {
        btnFacebook.setOnClickListener(getListenerForUrl(mContext.getString(R.string.link_facebook)));
        btnTwitter.setOnClickListener(getListenerForUrl(mContext.getString(R.string.link_twitter)));
        btnInstagram.setOnClickListener(getListenerForUrl(mContext.getString(R.string.link_instagram)));
        btnChrome.setOnClickListener(getListenerForUrl(mContext.getString(R.string.link_chrome)));
        btnSpotify.setOnClickListener(getListenerForUrl(mContext.getString(R.string.link_spotify)));
        btnYoutube.setOnClickListener(getListenerForUrl(mContext.getString(R.string.link_youtube)));
    }

    private OnClickListener getListenerForUrl(final String url) {
        return new OnClickListener() {
            @Override
            public void onClick(View v) {
                goToUrl(url);
            }
        };
    }

    private void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        mContext.startActivity(launchBrowser);
    }

}
