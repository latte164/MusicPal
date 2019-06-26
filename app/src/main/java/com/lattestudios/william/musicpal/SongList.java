package com.lattestudios.william.musicpal;

import java.util.List;

public class SongList {

    List<Song> songList;
    String name;

    public SongList(String name, List<Song> songList) {
        this.name = name;
        this.songList = songList;
    }

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public List<Song> getSongList() {return songList;}

    public void setSongList(List<Song> songList) {this.songList = songList;}

}
