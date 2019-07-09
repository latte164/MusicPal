package com.lattestudios.william.musicpal;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {

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
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        appDb = AppDatabase.getInstance(getActivity().getApplicationContext());
        songListDAO = appDb.getSongListDAO();
        parentList = songListDAO.getSongLists();

        recAdapter = new MainAdapter(getActivity().getApplicationContext(), parentList);
        recLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recView = view.findViewById(R.id.listRecView);
        recView.setLayoutManager(recLayoutManager);
        recView.setItemAnimator(new DefaultItemAnimator());
        recView.setAdapter(recAdapter);

        recAdapter.notifyDataSetChanged();

        // Inflate the layout for this fragment
        return view;
    }

}
