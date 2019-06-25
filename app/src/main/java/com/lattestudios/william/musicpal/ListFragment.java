package com.lattestudios.william.musicpal;


import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {

    private List<Song> songList;

    private RecyclerView recView;
    private MainAdapter recAdapter;
    private RecyclerView.LayoutManager recLayoutManager;

    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        songList = new ArrayList<Song>();
        recAdapter = new MainAdapter(getActivity().getApplicationContext(), songList);
        recLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 1);
        recView = (RecyclerView) view.findViewById(R.id.listRecView);
        recView.setLayoutManager(recLayoutManager);
        recView.setItemAnimator(new DefaultItemAnimator());
        recView.setAdapter(recAdapter);

        songList.add(new Song("Titus Was Born", "Young the Giant", R.drawable.thumbnail1));
        songList.add(new Song("Oblivion", "Young the Giant", R.drawable.thumbnail2));
        songList.add(new Song("I Want It All", "Coin", R.drawable.thumbnail1));
        recAdapter.notifyDataSetChanged();

        // Inflate the layout for this fragment
        return view;
    }

}
