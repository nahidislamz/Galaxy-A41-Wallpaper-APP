package com.nahidislamz.samsunggalaxym41wallpaper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.AlertDialog;
import android.app.WallpaperManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.nahidislamz.samsunggalaxym41wallpaper.ui.common.Common;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;


public class ViewPagerActivity extends AppCompatActivity {
    static  final int PERMISSION_REQUEST_CODE =1000;
    ViewPager viewPager2;
    ViewPagerAdapter viewPagerAdapter;
    AlertDialog alertDialog;
    FloatingActionButton setWallpaper,downloads;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_view_pager);

        setWallpaper=findViewById(R.id.setwallpaper);
        downloads=findViewById(R.id.download);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        Common.WIDTH = display.getWidth();
        Common.HIEGHT=display.getHeight();
        setWallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewPagerActivity.this.setWallpaperScreen();
            }
        });
        downloads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(ViewPagerActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        ViewPagerActivity.this.requestPermissions(new String[]
                                {Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
                    }
                } else {
                    ViewPagerActivity.this.downloadBitmap();
                }
            }
        });
        addViewPager();

    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == PERMISSION_REQUEST_CODE && grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            downloadBitmap();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    private void setWallpaperScreen() {
        alertDialog = new AlertDialog.Builder(ViewPagerActivity.this).create();
        alertDialog.setMessage("Setting Wallpaper... Please Wait");
        alertDialog.show();

        Glide.with(ViewPagerActivity.this)
                .asBitmap().load(Common.PIC_LIST.get(viewPager2.getCurrentItem()))
                .override(Common.WIDTH,Common.HIEGHT)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());

                        try {
                            wallpaperManager.setBitmap(resource);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        alertDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"Wallpaper Set",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });


    }
    public void addViewPager(){

        if(getIntent()!=null){
            viewPager2=findViewById(R.id.view_pager);
            int pos=getIntent().getIntExtra("pos",0);
            viewPagerAdapter = new ViewPagerAdapter(this, Common.PIC_LIST);
            viewPager2.setAdapter(viewPagerAdapter);
            viewPager2.setCurrentItem(pos);
            viewPagerAdapter.notifyDataSetChanged();
        }

    }


    private void downloadBitmap() {
        alertDialog = new AlertDialog.Builder(ViewPagerActivity.this).create();
        alertDialog.setMessage("Downloading... Please Wait");
        alertDialog.show();
        Glide.with(ViewPagerActivity.this)
                .asBitmap().load(Common.PIC_LIST.get(viewPager2.getCurrentItem()))
                .override(Common.WIDTH,Common.HIEGHT)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                        try {
                            saveBitmap(resource);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }
    public File saveBitmap(Bitmap bmp) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        File f = new File(Environment.getExternalStorageDirectory()
                + File.separator + UUID.randomUUID() + ".jpg");
        f.createNewFile();
        FileOutputStream fo = new FileOutputStream(f);
        fo.write(bytes.toByteArray());
        fo.close();
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(f)));
        Toast.makeText(this,"Download Successful",Toast.LENGTH_LONG).show();
        alertDialog.dismiss();

        return f;
    }

}
