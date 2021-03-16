package com.zulham.gitroom.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ModelRepo(
    var name: String? = "",
    var push: String? = ""
): Parcelable
