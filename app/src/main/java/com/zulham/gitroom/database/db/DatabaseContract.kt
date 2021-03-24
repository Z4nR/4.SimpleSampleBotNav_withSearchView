package com.zulham.gitroom.database.db

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {

    const val AUTHOR = "com.zulham.gitroom"
    const val SCHEME = "content"

    class FavColumns: BaseColumns {
        companion object{
            const val TABLE_NAME = "favourite"
            const val USER_URL = "user_url"
            const val IMG_USER = "user_image"
            const val USER_ID = "user_id"
            const val USER_NAME = "user_name"
            const val IS_FAV = "is_fav"
            const val LOGIN = "login"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHOR)
                .appendPath(TABLE_NAME)
                .build()

        }
    }

}