package com.walhalla.data.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "downloads")
public class DownloadEntity {

    @PrimaryKey
    @NonNull
    public String id; // ID загруженного файла

    public String fileName;
    public boolean posted;


    public DownloadEntity(@NonNull String id, String fileName) {
        this.id = id;
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "{" +
                "id='" + id + '\'' +
                ", fileName='" + fileName + '\'' +
                ", posted=" + posted +
                '}';
    }
}