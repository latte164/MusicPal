package com.lattestudios.william.musicpal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private List<Song> songList;
    private Context context;

    public static class SearchViewHolder extends RecyclerView.ViewHolder {

        public TextView title, artist;
        public ImageView thumbnail, overflow;

        public SearchViewHolder(View v) {
            super(v);

            title = v.findViewById(R.id.song_title);
            artist = v.findViewById(R.id.artist);
            thumbnail = v.findViewById(R.id.thumbnail);
            overflow = v.findViewById(R.id.overflow);

        }
    }

    public SearchAdapter(List<Song> songList, Context context) {
        this.context = context;
        this.songList = songList;
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_card, parent, false);
        return new SearchViewHolder(itemView);
    }

    public void onBindViewHolder(final SearchViewHolder holder, int position) {

        final int cardPos = position;

        Song song = songList.get(position);
        holder.title.setText(song.getName());
        holder.artist.setText("by " + song.getArtist());

        Glide.with(context).load(song.getThumbnail()).into(holder.thumbnail);

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(holder.overflow, cardPos);
            }
        });

    }

    private void showPopup(View view, int position) {

        PopupMenu popupMenu = new PopupMenu(context, view);
        MenuInflater menuInflater = popupMenu.getMenuInflater();
        menuInflater.inflate(R.menu.search_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new MenuItemClickListener(position));
        popupMenu.show();
    }

    class MenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        int position;

        public MenuItemClickListener(int position){
            this.position = position;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch(menuItem.getItemId()) {
                case R.id.menu_add_song:

                    addingAlertDialog(position);

            }
            return false;

        }

    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    private void addingAlertDialog(final int position) {

        //get song lists and names for the dialog
        final SongListDAO songListDAO = AppDatabase.getInstance(context.getApplicationContext()).getSongListDAO();
        final List<SongList> songLists = songListDAO.getSongLists();
        String[] listNames = new String[songLists.size()];
        for(int i = 0; i < songLists.size(); i++)
            listNames[i] = songLists.get(i).name;

        AlertDialog.Builder b = new AlertDialog.Builder(context);
        b.setTitle("Pick a Playlist");
        b.setItems(listNames, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

                //get Songs and add new
                Songs songs = songLists.get(which).getSongList();
                songs.addSong(songList.get(position));

                //add the new Songs object to the song list
                SongList sl = songLists.get(which);
                sl.setSongList(songs);

                //update the updated Song List in the data base
                songListDAO.update(sl);

                Toast.makeText(context, "Added " + songList.get(position).getName(), Toast.LENGTH_SHORT).show();

            }
        });

        b.show();

    }

}
