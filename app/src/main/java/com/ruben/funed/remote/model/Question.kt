package com.ruben.funed.remote.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by ruben.quadros on 19/06/21.
 **/
data class Question(
    val id: String,
    val qno: Long,
    val text: String,
    val mcOptions: List<String>,
    val type: String,
    val marks: Long
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readLong(),
        parcel.readString() ?: "",
        parcel.createStringArrayList() ?: arrayListOf(),
        parcel.readString() ?: "",
        parcel.readLong()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeLong(qno)
        parcel.writeString(text)
        parcel.writeStringList(mcOptions)
        parcel.writeString(type)
        parcel.writeLong(marks)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Question> {
        override fun createFromParcel(parcel: Parcel): Question {
            return Question(parcel)
        }

        override fun newArray(size: Int): Array<Question?> {
            return arrayOfNulls(size)
        }
    }
}