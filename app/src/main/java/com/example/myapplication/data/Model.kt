package com.example.myapplication.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Model() : Parcelable {

    @SerializedName("restaurant")
    @Expose
    var restaurant: List<Restaurant>? = null

    constructor(parcel: Parcel) : this() {
        restaurant = parcel.createTypedArrayList(Restaurant)
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeList(restaurant)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Model> {
        override fun createFromParcel(parcel: Parcel): Model {
            return Model(parcel)
        }

        override fun newArray(size: Int): Array<Model?> {
            return arrayOfNulls(size)
        }
    }


}
