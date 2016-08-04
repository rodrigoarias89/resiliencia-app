package ar.com.lapotoca.resiliencia.ui.custom;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ar.com.lapotoca.resiliencia.R;
import ar.com.lapotoca.resiliencia.gallery.ui.ImageListFragment;
import ar.com.lapotoca.resiliencia.ui.SongsFragment;

/**
 * Created by rarias on 8/2/16.
 */
public class TabsAdapter extends FragmentPagerAdapter {

    private final Context mContext;


    public TabsAdapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager);
        mContext = context;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return SongsFragment.newInstance();
            case 1:
                return ImageListFragment.newInstance();
//                return ImageGridFragment.newInstance();
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.tab_allmusic_title);
            case 1:
                return mContext.getString(R.string.tab_art_title);
        }
        return "";
    }
}
