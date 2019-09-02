package com.lattestudios.william.musicpal;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {

    View v;
    public List<SongList> parentList;

    private AppDatabase appDb;
    private SongListDAO songListDAO;

    private RecyclerView recView;
    private MainAdapter recAdapter;
    private RecyclerView.LayoutManager recLayoutManager;

    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_list, container, false);

        appDb = AppDatabase.getInstance(getActivity().getApplicationContext());
        songListDAO = appDb.getSongListDAO();
        parentList = songListDAO.getSongLists();

        recAdapter = new MainAdapter(getActivity().getApplicationContext(), parentList);
        recLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recView = v.findViewById(R.id.listRecView);
        recView.setLayoutManager(recLayoutManager);
        recView.setItemAnimator(new DefaultItemAnimator());
        recView.setAdapter(recAdapter);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setElevation(0);

        recAdapter.notifyDataSetChanged();

        checkPermissions(Manifest.permission.INTERNET);
        checkPermissions(Manifest.permission.ACCESS_NETWORK_STATE);

        // Inflate the layout for this fragment
        return v;
    }

    private boolean checkPermissions(String permission) {

        if(ContextCompat.checkSelfPermission(v.getContext(), permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, 1);
            return false;
        }
        return true;

    }

}
