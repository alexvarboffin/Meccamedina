package com.walhalla.data.repository;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;

import com.walhalla.data.database.FavoriteDatabase;
import com.walhalla.data.model.DownloadEntity;

import java.io.File;
import java.util.List;

public class LocalDatabaseRepo {

    private static LocalDatabaseRepo instance;

    private static final Object LOCK = new Object();


    private final FavoriteDatabase db;

    public LocalDatabaseRepo(Context context) {
        db = Room.databaseBuilder(context.getApplicationContext(), FavoriteDatabase.class, "favdbx")
                .allowMainThreadQueries()
                .build();
    }

    public synchronized static LocalDatabaseRepo getStoreInfoDatabase(Context context) {
        if (instance == null) {
            synchronized (LOCK) {
                if (instance == null) {

                    instance = new LocalDatabaseRepo(context);
                }
            }
        }
        return instance;
    }

    //
//    public List<DownloadEntity> getFavorite() {
//        return db.favoriteDao().getFavoriteData();
//    }
//
//    public int isFavorite(String tvgId) {
//        return db.favoriteDao().isFavorite(tvgId);
//    }
//
//    public void addFavorite(DownloadEntity channel) {
//        db.favoriteDao().addData(channel);
//    }
//
//    public void deleteFavorite(String tvgId) {
//        db.favoriteDao().delete(tvgId);
//    }
//
    public void insertPosted(DownloadEntity entity) {
        try {
            Log.d("@@@@", "insertPosted: ");
            entity.posted = true;
            db.channelDao().insertPosted(entity);
            //File file = new File("/storage/emulated/0/Movies", entity.id + ".mp4");
            //boolean exist = file.delete();
        } catch (Exception e) {
            Log.d("@@@@", "insertPosted: "+e);
        }
    }

    public void insertNewValue(DownloadEntity entity) {
        try {Log.d("@@@@", "insertinsertNewValue: ");
            entity.posted = false;
            db.channelDao().insertValue(entity);
            //File file = new File("/storage/emulated/0/Movies", entity.id + ".mp4");
        } catch (Exception e) {
            Log.d("@@@@", "insertPosted: "+e);
        }
    }

    public boolean isDownloadExists(String id) {
        try {
            return db.channelDao().isPosted(id) > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public DownloadEntity selectNotPosted() {
        try {
            return db.channelDao().getNotPosted();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<DownloadEntity> selectAll() {    try {
        return db.channelDao().selectAll();
    } catch (Exception e) {
        e.printStackTrace();
    }
        return null;
    }
}