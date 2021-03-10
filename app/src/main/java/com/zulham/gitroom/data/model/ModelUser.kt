package com.zulham.gitroom.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ModelUser (
        var username: String?,
        var name: String?,
        var company: String?,
        var follower: String?,
        var following: String?,
        var location: String?,
        var repository: String?,
        var photo: Int
) : Parcelable