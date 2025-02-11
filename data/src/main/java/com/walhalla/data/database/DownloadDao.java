package com.walhalla.data.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.walhalla.data.model.DownloadEntity;

import androidx.room.OnConflictStrategy;

import java.util.List;

@Dao
public interface DownloadDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPosted(DownloadEntity downloadEntity);


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertValue(DownloadEntity downloadEntity);

    @Query("SELECT COUNT(*) FROM downloads WHERE id = :id and posted=1")
    int isPosted(String id);

    @Query("SELECT * FROM downloads WHERE posted=0")
    DownloadEntity getNotPosted();

    @Query("SELECT * FROM downloads")
    List<DownloadEntity> selectAll();
}