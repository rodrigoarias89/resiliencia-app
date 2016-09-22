package ar.com.lapotoca.resiliencia.utils;

/**
 * Created by rarias on 7/21/16.
 */
public class DurationUtils {

    public static String getMilisecondsToMMSS(int ms) {
        int secondsTotal = ms / 1000;
        int minutes = secondsTotal / 60;
        int seconds = secondsTotal - (minutes * 60);
        return String.format("%02d", minutes) + ":" + String.format("%02d", seconds);
    }
}
