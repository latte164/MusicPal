package com.lattestudios.william.musicpal;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {

    private RecyclerView recView;
    private RecyclerView.Adapter recAdapter;
    private RecyclerView.LayoutManager recLayoutManager;

    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list, container, false);

        /*recView = (RecyclerView) view.findViewById(R.id.mainRecView);
        recView.hasFixedSize();
        recLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recView.setLayoutManager(recLayoutManager);
        recAdapter = new MainAdapter();
        recView.setAdapter(recAdapter);*/


        // Inflate the layout for this fragment
        return view;
    }

}
