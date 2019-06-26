package com.lattestudios.william.musicpal;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {

    private Context context;
    private List<SongList> parentList;

    public static class MainViewHolder extends RecyclerView.ViewHolder {

        public RecyclerView recyclerView;
        public TextView viewLabel;

        public MainViewHolder(View v) {
            super(v);

            viewLabel = v.findViewById(R.id.horizontalLabel);
            recyclerView = v.findViewById(R.id.horizontalRecyclerView);

        }
    }

    public MainAdapter(Context context, List<SongList> parentList) {
        this.context = context;
        this.parentList = parentList;
    }

        @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_recycler_view, parent, false);
        return new MainViewHolder(itemView);
    }

    public void onBindViewHolder(final MainViewHolder holder, int position) {

        SongList songList = parentList.get(position);

        holder.viewLabel.setText(songList.name);

        ChildAdapter recAdapter = new ChildAdapter(context, songList.getSongList());
        RecyclerView.LayoutManager recLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recView = holder.recyclerView;
        recView.setLayoutManager(recLayoutManager);
        recView.setItemAnimator(new DefaultItemAnimator());
        recView.setAdapter(recAdapter);


    }

    @Override
    public int getItemCount() {
        return parentList.size();
    }


}
