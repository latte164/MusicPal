package com.lattestudios.william.musicpal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.preference.DialogPreference;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {

    private Context context;
    private List<SongList> parentList;

    public static class MainViewHolder extends RecyclerView.ViewHolder {

        public RecyclerView recyclerView;
        public TextView viewLabel;
        public ImageView deleteIcon;

        public MainViewHolder(View v) {
            super(v);

            viewLabel = v.findViewById(R.id.horizontalLabel);
            recyclerView = v.findViewById(R.id.horizontalRecyclerView);
            deleteIcon = v.findViewById(R.id.deleteImage);

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

    public void onBindViewHolder(final MainViewHolder holder, final int position) {

        final SongList songList = parentList.get(position);

        holder.viewLabel.setText(songList.name);
        holder.deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder b = new AlertDialog.Builder(v.getContext());
                b.setTitle("Delete Playlist");
                b.setMessage("Are you sure you would like to delete this playlist?");
                b.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SongListDAO songListDAO = AppDatabase.getInstance(context.getApplicationContext()).getSongListDAO();
                        songListDAO.delete(songList);
                        parentList.remove(position);
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                b.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                b.show();

            }
        });

        ChildAdapter recAdapter = new ChildAdapter(context, songList.getSongList().getSongs(), songList.name);
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
