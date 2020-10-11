package com.nahidislamz.samsunggalaxym41wallpaper;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;
import com.nahidislamz.samsunggalaxym41wallpaper.ui.common.Common;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashscreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splashscreen);



        if(Common.isConnectToInternet(getApplicationContext())){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    SplashscreenActivity.this.startActivity(new Intent(SplashscreenActivity.this, MainActivity.class));
                    SplashscreenActivity.this.finish();
                }
            },3000);
        }
        else {
            showNoInternet("You need active internet to use this app");
        }


    }

    private void showNoInternet(String s) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("You don't have internet");
        builder.setMessage(s);
        builder.setIcon(R.drawable.no_internet);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        SplashscreenActivity.this.finish();
                    }
                }, 1000);
            }
        });

        builder.setCancelable(false);
        builder.show();
    }

}
