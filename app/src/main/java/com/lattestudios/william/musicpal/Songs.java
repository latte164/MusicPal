package com.lattestudios.william.musicpal;

import java.util.ArrayList;
import java.util.List;

public class Songs {

    private final String DATA_SEP_KEY = "@%";
    private final String SONG_SEP_KEY = "`#";
    private List<Song> songs;

    public Songs() {
        this.songs = new ArrayList<Song>();
        this.songs.add(new Song("Add New Songs!", "Autofill", R.drawable.empty_thumbnail));
    }

    public Songs(List<Song> songs) {
        this.songs = songs;
    }

    public Songs(String dbData) {
        String[] songStrings = dbData.split(SONG_SEP_KEY);
        List<Song> songs = new ArrayList<Song>();

        for(String s : songStrings)
            songs.add(new Song(s.split(DATA_SEP_KEY)));

        this.songs = songs;
    }

    public List<Song> getSongs() { return songs; }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public void addSong(Song song) {

        if(songs.size() == 1 && songs.get(0).getName().equals("Add New Songs!"))
            songs.remove(0);

        songs.add(song);
    }

    public String dbConverter() {
        String out = "";

        for(Song s : songs){
            String[] data = s.getDbFriendlyData();
            out += data[0] + DATA_SEP_KEY + data[1] + DATA_SEP_KEY + data[2] + SONG_SEP_KEY;
        }

        return out;
    }

}
