package com.example.projectkoramen.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SettingModel(
    var name: String? = null,
    var phoneNumber: String? = null,
) : Parcelable