package com.zulham.gitroom.database.db

import android.net.Uri
import android.provider.BaseColumns

internal class DatabaseContract {

    internal class FavColumns: BaseColumns {
        companion object{
            const val TABLE_NAME = "favourite"
            const val IMG_USER = "user_image"
            const val USER_ID = "user_id"
            const val USER_NAME = "user_name"
            const val USER_URL = "user_url"
            const val IS_FAV = "is_fav"

        }
    }

}