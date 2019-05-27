package com.example.chatkotlinfirebase

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


//yg ini caranya agar dapat mem parse data

@Parcelize
class User(
           val uid :String,
           val name :String,
           val photo :String,
           val email :String):Parcelable {

    constructor():this("","","","")
}