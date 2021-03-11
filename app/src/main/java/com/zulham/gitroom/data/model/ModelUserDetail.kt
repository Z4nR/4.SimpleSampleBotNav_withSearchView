package com.zulham.gitroom.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ModelUserDetail(
        var login: String,
        var avatar_url: String,
        var name: String,
        var location: String,
        var company: String,
        var repository: Int,
        var follower: Int,
        var following: Int
): Parcelable