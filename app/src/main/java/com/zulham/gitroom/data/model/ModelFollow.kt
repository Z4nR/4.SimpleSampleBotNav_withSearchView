package com.zulham.gitroom.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class ModelFollow (
    var id: Int? = 0,
    var login: String? = "",
    var avatar_url: String? = ""
): Parcelable
