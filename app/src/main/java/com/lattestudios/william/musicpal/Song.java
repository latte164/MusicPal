package com.lattestudios.william.musicpal;

public class Song {

    private String name,artist;
    private int thumbnail;

    public Song() {
    }

    public Song(String name, String artist, int thumbnail) {
        this.name = name;
        this.artist = artist;
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String setArtist() {
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

}
