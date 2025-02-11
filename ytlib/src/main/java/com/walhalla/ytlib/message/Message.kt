package com.walhalla.ytlib.message

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

class Message : Serializable {
    @JvmField
    @SerializedName("text")
    @Expose
    var text: String? = null

    @JvmField
    @SerializedName("user")
    @Expose
    var user: String? = null

    @JvmField
    @SerializedName("user_id")
    @Expose
    var user_id: String = "null"

    @JvmField
    @SerializedName("time")
    @Expose
    var time: Long = 0

    constructor(messageText: String?, messageUser: String?, messageUserId: String) {
        text = messageText
        user = messageUser
        time = Date().time
        user_id = messageUserId
    }
    constructor(messageText: String?, messageUser: String?) {
        text = messageText
        user = messageUser
        time = Date().time
        user_id = Date().time.toString()
    }
    constructor() {}
}