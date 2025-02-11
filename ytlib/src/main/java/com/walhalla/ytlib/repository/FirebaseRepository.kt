package com.walhalla.ytlib.repository

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.walhalla.ui.DLog
import com.walhalla.ytlib.database.ListEntryDB
import com.walhalla.ytlib.domen.ListEntryUI
import java.util.LinkedList

class FirebaseRepository : Repository {
    fun loadPlaylist(callback: Repository.Callback<List<ListEntryUI>>) {
        try {
            val reference = FirebaseDatabase.getInstance().getReference("live")
            reference //.child("/")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        //showLoader();
//                                if (mainCallback != null) {
//                                    mainCallback.hideLoader();
//                                }
                        val list: MutableList<ListEntryDB> = ArrayList()
                        for (obj in snapshot.children) {
                            try {
                                val category = obj.getValue(ListEntryDB::class.java)
                                if (category?.videoId != null) {
                                    list.add(category)
                                }
                            } catch (e: Exception) {
                                DLog.handleException(e)
                                callback.onRetrievalFailed("Failed to getUrl value.", e)
                            }
                        }
                        if (!list.isEmpty()) {
                            val listEntries: MutableList<ListEntryUI> = LinkedList()
                            for (i in list.indices) {
                                val item0 = list[i]
                                val obj = ListEntryUI(
                                    item0.videoId,
                                    item0.title,
                                    item0.duration,
                                    item0.urlThumbnails,
                                    item0.publishedAt,
                                    item0.type
                                )
                                listEntries.add(obj)
                            }
                            callback.onMessageRetrieved(listEntries)
                        } else {
                            callback.onRetrievalFailed(
                                "Database is empty, reinstall the Application",
                                null
                            )
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        callback.onRetrievalFailed(error.message, error.toException())
                    }
                })
            //reference.addChildEventListener(childEventListener);
        } catch (e: Exception) {
            DLog.handleException(e)
            callback.onRetrievalFailed("loadCategory: ", e)
        }
    }

    fun saveList(uiList: List<ListEntryUI>) {
        try {
            val ref = FirebaseDatabase.getInstance().getReference("live")
            val list: MutableList<ListEntryDB> = ArrayList()
            for (i in uiList.indices) {
                val item0 = uiList[i]
                val obj = ListEntryDB(
                    item0.videoId,
                    item0.title, item0.duration, item0.urlThumbnails, item0.publishedAt, item0.type
                )
                list.add(obj)
            }
            ref.setValue(list).addOnFailureListener { e: Exception? ->
                DLog.handleException(
                    e
                )
            }
        } catch (e: Exception) {
            DLog.handleException(e)
            //callback.onRetrievalFailed("loadCategory: ", e);
        }
    }
}
