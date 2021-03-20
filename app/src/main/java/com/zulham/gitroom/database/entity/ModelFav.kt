package com.zulham.gitroom.database.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ModelFav(
    var id: Int? = 0,
    var login: String? = "",
    var avatar_url: String? = "",
    var url: String? = ""
): Parcelable
