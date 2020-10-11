package com.nahidislamz.samsunggalaxym41wallpaper.ui.about;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.mikepenz.fastadapter.BuildConfig;
import com.nahidislamz.samsunggalaxym41wallpaper.R;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class AboutFragment extends Fragment {



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Element versionElement = new Element();
        versionElement.setTitle("Version Name : "+ BuildConfig.VERSION_NAME);

        View aboutPage = new AboutPage(getContext())
                .isRTL(false)
                .setImage(R.mipmap.ic_launcher_round)
                .setDescription("Get all the latest iPhone Wallpaper here")
                .addItem(versionElement)
                .addItem(new Element().setTitle("Developer : Nahid Islam"))
                .addGroup("Privacy & Terms")
                .addWebsite("https://sites.google.com/view/iphone12privacy/home","Privacy Policy")
                .addWebsite("https://sites.google.com/view/iphone12terms/home","Terms & Conditions")
                .addGroup("Developer Contact")
                .addEmail("nahidislamz@outlook.com","Email")
                .create();


        return aboutPage;
    }
}