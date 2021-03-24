package com.zulham.consumergitroom.db.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ModelFav(
        var id: Int? = 0,
        var login: String? = "",
        var avatar_url: String? = "",
        var url: String? = "",
        var name: String? = ""
): Parcelable
