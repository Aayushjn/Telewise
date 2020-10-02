package com.aayush.telewise.util.android

import android.os.Parcel
import android.os.Parcelable

interface KParcelable : Parcelable {
    override fun describeContents(): Int = 0
    override fun writeToParcel(dest: Parcel, flags: Int)
}

inline fun <reified T> parcelableCreator(crossinline creator: (Parcel) -> T) =
    object : Parcelable.Creator<T> {
        override fun createFromParcel(source: Parcel): T = creator(source)
        override fun newArray(size: Int): Array<T?> = arrayOfNulls(size)
    }

fun Parcel.readBool(): Boolean = readInt() != 0

fun Parcel.writeBool(value: Boolean) = writeInt(if (value) 1 else 0)

inline fun <reified T> Parcel.readNullable(reader: Parcel.() -> T): T? = if (readInt() != 0) reader() else null

inline fun <reified T> Parcel.writeNullable(value: T?, writer: Parcel.(T) -> Unit) =
    if (value != null) {
        writeInt(1)
        writer(value)
    } else {
        writeInt(0)
    }

inline fun <reified T : Parcelable> Parcel.readParcelableListCompat(list: MutableList<T>, cl: ClassLoader?): List<T> {
    val n = readInt()
    if (n == -1) {
        list.clear()
        return list
    }

    val m = list.size
    var i = 0
    while (i < m && i < n) {
        list[i] = readParcelable<Parcelable>(cl) as T
        i++
    }
    while (i < n) {
        list.add(readParcelable<Parcelable>(cl) as T)
        i++
    }
    while (i < m) {
        list.removeAt(n)
        i++
    }
    return list
}

inline fun <reified T : Parcelable> Parcel.writeParcelableListCompat(value: List<T>?, flags: Int) {
    if (value == null) {
        writeInt(-1)
        return
    }

    val n: Int = value.size
    var i = 0
    writeInt(n)
    while (i < n) {
        writeParcelable(value[i], flags)
        i++
    }
}
