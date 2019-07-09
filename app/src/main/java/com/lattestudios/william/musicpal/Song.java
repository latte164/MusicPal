package com.lattestudios.william.musicpal;

public class Song {

    private String name;
    private String artist;
    private int thumbnail;

    public Song() {

    }

    public Song(String name, String artist, int thumbnail) {
        this.name = name;
        this.artist = artist;
        this.thumbnail = thumbnail;
    }

    public Song(String[] dbFriendlyData) {
        if(dbFriendlyData.length < 3)
            return;

        name = dbFriendlyData[0];
        artist = dbFriendlyData[1];
        thumbnail = Integer.valueOf(dbFriendlyData[2]);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String[] getDbFriendlyData() {
        String[] data = {name, artist, String.valueOf(thumbnail)};
        return data;
    }

}
