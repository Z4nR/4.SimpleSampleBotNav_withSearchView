package com.zulham.gitroom.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ModelUser (
        var id: Int,
        var login: String,
        var avatar_url: String,
        var url: String
) : Parcelable