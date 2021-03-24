package com.zulham.gitroom.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ModelUserDetail(
        var login: String? = "",
        var avatar_url: String? = "",
        var name: String? = "",
        var location: String? = "",
        var company: String? = "",
        var url: String? = "",
        var repository: Int? = 0,
        var follower: Int? = 0,
        var following: Int? = 0,
        var id: Int? = 0
): Parcelable