package com.nahidislamz.samsunggalaxym41wallpaper.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mikepenz.fastadapter.IAdapter;
import com.mikepenz.fastadapter.listeners.OnClickListener;
import com.nahidislamz.samsunggalaxym41wallpaper.R;
import com.nahidislamz.samsunggalaxym41wallpaper.ViewPagerActivity;
import com.nahidislamz.samsunggalaxym41wallpaper.models.Wallpaper;
import com.nahidislamz.samsunggalaxym41wallpaper.ui.common.Common;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;
import com.mikepenz.fastadapter.commons.utils.FastAdapterDiffUtil;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeFragment extends Fragment {

    private List<Wallpaper> wallpaperList;
    private List<String> picturesList;
    private ProgressBar progressBar;
    private FastItemAdapter<Wallpaper> itemAdapter;
    private RecyclerView recyclerView;
    private int uid;
    private String url;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        FirebaseApp.initializeApp(root.getContext());
        progressBar = root.findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView = root.findViewById(R.id.wall_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        itemAdapter = new FastItemAdapter<>();
        wallpaperList = new ArrayList<>();
        picturesList = new ArrayList<>();
        recyclerView.setAdapter(itemAdapter);

        FirebaseDatabase.getInstance().getReference("glaxya41")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            progressBar.setVisibility(View.GONE);
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                url = ds.child("url").getValue(String.class);
                                Wallpaper wallpaper = new Wallpaper(uid, url);uid++;
                                wallpaperList.add(wallpaper);
                                picturesList.add(url);
                                Collections.reverse(wallpaperList);
                                Collections.reverse(picturesList);
                            }

                        }

                        DiffUtil.DiffResult result = FastAdapterDiffUtil.calculateDiff(itemAdapter, wallpaperList);
                        FastAdapterDiffUtil.set(itemAdapter,result);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getContext(), (CharSequence) databaseError.toException(),Toast.LENGTH_LONG).show();

                    }
                });
        itemAdapter.withSelectable(true);
        itemAdapter.withOnClickListener(new OnClickListener<Wallpaper>() {
            @Override
            public boolean onClick(@Nullable View v, IAdapter<Wallpaper> adapter, Wallpaper item, int position) {

                Intent intent = new Intent(HomeFragment.this.getContext(), ViewPagerActivity.class);
                intent.putExtra("pos", position);
                Common.PIC_LIST = picturesList;
                HomeFragment.this.startActivity(intent);
                return true;
            }
        });

        return root;
    }
}