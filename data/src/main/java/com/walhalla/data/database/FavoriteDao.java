//package com.walhalla.data.database;
//
//
//import androidx.room.Dao;
//import androidx.room.Insert;
//import androidx.room.Query;
//
//import com.walhalla.data.model.DownloadEntity;
//
//import java.util.List;
//
//@Dao
//public interface FavoriteDao {
//
//    @Insert
//    void addData(DownloadEntity favoriteList);
//
//    @Query("select * from favorite")
//    List<DownloadEntity> getFavoriteData();
//
////    @Query("SELECT EXISTS (SELECT 1 FROM favorite WHERE _id=:id)")
////    int isFavorite(long id);
//
////    @Query("SELECT EXISTS (SELECT 1 FROM favorite WHERE tvgId=:tvGid)")
////    int isFavorite(String tvGid);
//
//    @Query("SELECT 1 FROM favorite WHERE tvgId=:tvGid")
//    int isFavorite(String tvGid);
//
////    @Delete
////    void delete(Channel favoriteList);
//
//    @Query("DELETE FROM favorite WHERE tvgId=:tvGid")
//    void delete(String tvGid);
//}
