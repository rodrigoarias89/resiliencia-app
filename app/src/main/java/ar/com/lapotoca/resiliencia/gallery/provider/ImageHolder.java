package ar.com.lapotoca.resiliencia.gallery.provider;

/**
 * Created by rarias on 7/27/16.
 */
public class ImageHolder {

    private boolean local;
    private String url;

    public ImageHolder(boolean local, String url) {
        this.local = local;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public boolean isLocal() {
        return local;
    }
}
