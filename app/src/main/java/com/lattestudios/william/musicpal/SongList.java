package com.lattestudios.william.musicpal;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class SongList {

    String songs; //was just a List<Song>
    @PrimaryKey
    @NonNull
    String name;

    @Ignore
    public SongList(String name) {
        this(name, new Songs());
    }

    public SongList(String name, Songs songs) {
        this(name, songs.dbConverter());
    }

    public SongList(String name, String songs){
        this.name = name;
        this.songs = songs;
    }

    @NonNull
    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public Songs getSongList() {return new Songs(songs);}

    public void setSongList(Songs songs) {this.songs = songs.dbConverter();}

}
