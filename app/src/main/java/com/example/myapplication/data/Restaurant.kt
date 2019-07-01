package com.example.myapplication.data

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class Restaurant : Parcelable {

    @SerializedName("id")
    @Expose
    var id: Id? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("address")
    @Expose
    var address: String? = null
    @SerializedName("time")
    @Expose
    var time: Int = 0
    @SerializedName("cuisines")
    @Expose
    var cuisines: String? = null

    protected constructor(`in`: Parcel) {
        this.id = `in`.readValue(Id::class.java!!.getClassLoader()) as Id
        this.name = `in`.readValue(String::class.java.classLoader) as String
        this.address = `in`.readValue(String::class.java.classLoader) as String
        this.time = `in`.readValue(Int::class.javaPrimitiveType!!.classLoader) as Int
        this.cuisines = `in`.readValue(String::class.java.classLoader) as String
    }

    /**
     * No args constructor for use in serialization
     *
     */
    constructor() {}

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeValue(id)
        dest.writeValue(name)
        dest.writeValue(address)
        dest.writeValue(time)
        dest.writeValue(cuisines)
    }

    override fun describeContents(): Int {
        return 0
    }


    companion object CREATOR : Creator<Restaurant> {
        override fun createFromParcel(parcel: Parcel): Restaurant {
            return Restaurant(parcel)
        }

        override fun newArray(size: Int): Array<Restaurant?> {
            return arrayOfNulls(size)
        }
    }

}
