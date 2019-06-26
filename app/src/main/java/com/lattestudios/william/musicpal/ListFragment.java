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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {

    private List<SongList> parentList;

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

        createTempLists();

        recAdapter = new MainAdapter(getActivity().getApplicationContext(), parentList);
        recLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recView = (RecyclerView) view.findViewById(R.id.listRecView);
        recView.setLayoutManager(recLayoutManager);
        recView.setItemAnimator(new DefaultItemAnimator());
        recView.setAdapter(recAdapter);


        recAdapter.notifyDataSetChanged();

        // Inflate the layout for this fragment
        return view;
    }

    private void createTempLists() {

        List<Song> songList1 = new ArrayList<Song>();
        List<Song> songList2 = new ArrayList<Song>();
        List<Song> songList3 = new ArrayList<Song>();
        parentList = new ArrayList<SongList>();

        songList1.add(new Song("Titus Was Born", "Young the Giant", R.drawable.thumbnail1));
        songList1.add(new Song("Oblivion", "Young the Giant", R.drawable.thumbnail2));
        songList1.add(new Song("I Want It All", "Coin", R.drawable.thumbnail1));

        songList2.add(new Song("Oblivion", "Young the Giant", R.drawable.thumbnail2));
        songList2.add(new Song("I Want It All", "Coin", R.drawable.thumbnail1));
        songList2.add(new Song("Titus Was Born", "Young the Giant", R.drawable.thumbnail1));

        songList3.add(new Song("I Want It All", "Coin", R.drawable.thumbnail1));
        songList3.add(new Song("Oblivion", "Young the Giant", R.drawable.thumbnail2));
        songList3.add(new Song("Titus Was Born", "Young the Giant", R.drawable.thumbnail1));
        songList3.add(new Song("Titus Was Born", "Young the Giant", R.drawable.thumbnail1));

        parentList.add(new SongList("Indie 1", songList1));
        parentList.add(new SongList("Indie 100000", songList2));
        parentList.add(new SongList("Indie Ok", songList3));

    }

}
