package com.walhalla.data.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.walhalla.data.model.DownloadEntity;


@Database(entities = {DownloadEntity.class}, version = 1)
public abstract class FavoriteDatabase extends RoomDatabase {

    //public abstract FavoriteDao favoriteDao();

    public abstract DownloadDao channelDao();


}
