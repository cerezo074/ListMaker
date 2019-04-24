package com.example.android.aprentice.elichigo

import android.os.Parcel
import android.os.Parcelable

class TaskList(val name: String, val task: ArrayList<String> = ArrayList<String>()): Parcelable {

    constructor(source: Parcel?): this(
        if (source != null) source.readString() else "",
        if (source != null) source.createStringArrayList() else ArrayList<String>(0)
    )

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(name)
        dest?.writeStringList(task)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR: Parcelable.Creator<TaskList> {

        override fun createFromParcel(source: Parcel?): TaskList {
            return TaskList(source)
        }

        override fun newArray(size: Int): Array<TaskList?> {
            return arrayOfNulls<TaskList>(size)
        }

    }


}