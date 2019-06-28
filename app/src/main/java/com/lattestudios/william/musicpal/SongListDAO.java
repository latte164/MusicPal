package com.lattestudios.william.musicpal;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface SongListDAO {

    @Insert
    public void insert(SongList... songList);

    @Update
    public void update(SongList... songList);

    @Delete
    public void delete(SongList songList);

    @Query("SELECT * FROM SongList WHERE name = :searchName")
    public SongList getSongListWithName(String searchName);

    @Query("SELECT * FROM SongList")
    public List<SongList> getSongLists();

}
