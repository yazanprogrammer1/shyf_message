package com.example.shyf_message.models

import android.os.Parcel
import android.os.Parcelable

data class User(
    var id: String? = "",
    val email: String? = null,
    val password: String? = null,
    val whatsappLink: String? = null,
    val facebookLink: String? = null,
    var name: String? = null,
    val information: String? = null,
    val userImage: String? = null,
    var userToken: String? = null,
    var online_status: Boolean? = false,
    var last_seen: String? = "",
    var text_status: String? = "",
    var global_chat_wallpaper: String? = ""


) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(email)
        parcel.writeString(password)
        parcel.writeString(whatsappLink)
        parcel.writeString(facebookLink)
        parcel.writeString(name)
        parcel.writeString(information)
        parcel.writeString(userImage)
        parcel.writeString(userToken)
        parcel.writeValue(online_status)
        parcel.writeString(last_seen)
        parcel.writeString(text_status)
        parcel.writeString(global_chat_wallpaper)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}