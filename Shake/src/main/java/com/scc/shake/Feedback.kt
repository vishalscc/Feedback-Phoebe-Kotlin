package com.scc.shake

import android.os.Parcel
import android.os.Parcelable

/**
 * Hold data of device info with feedback to send to server
 */
class Feedback(
    var text: String? = null,
    var deviceOS: String? = null,
    var deviceType: String? = null,
    var deviceModel: String? = null,
    var pageName: String? = null,
    var manufacturer: String? = null
) : Parcelable{

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(text)
        parcel.writeString(deviceOS)
        parcel.writeString(deviceType)
        parcel.writeString(deviceModel)
        parcel.writeString(pageName)
        parcel.writeString(manufacturer)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Feedback> {
        override fun createFromParcel(parcel: Parcel): Feedback {
            return Feedback(parcel)
        }

        override fun newArray(size: Int): Array<Feedback?> {
            return arrayOfNulls(size)
        }
    }


}