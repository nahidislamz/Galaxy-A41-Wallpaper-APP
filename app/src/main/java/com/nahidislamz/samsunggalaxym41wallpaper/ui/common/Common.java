package com.nahidislamz.samsunggalaxym41wallpaper.ui.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.List;

public class Common {

    public static List<String> PIC_LIST= null;
    public static int WIDTH = 0;
    public static int HIEGHT = 0;

    public static boolean isConnectToInternet(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();

        if(info != null){
            return true;
        }
        return false;
    }
}
