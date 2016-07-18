package ar.com.lapotoca.resiliencia.utils;

import android.content.Context;
import android.widget.Toast;

public class NotificationHelper {

    public static void showNotification(Context context, String msg) {
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, msg, duration);
        toast.show();
    }
}
