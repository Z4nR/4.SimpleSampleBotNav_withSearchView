package com.zulham.gitroom.database.mapping

import android.database.Cursor
import com.zulham.gitroom.database.db.DatabaseContract
import com.zulham.gitroom.database.entity.ModelFav

object MappingHelper {

    fun mapCursorToArrayList(favCursor: Cursor?): ArrayList<ModelFav> {
        val favList = ArrayList<ModelFav>()
        favCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.FavColumns.USER_ID))
                val login = getString(getColumnIndexOrThrow(DatabaseContract.FavColumns.LOGIN))
                val userImg = getString(getColumnIndexOrThrow(DatabaseContract.FavColumns.IMG_USER))
                val userUrl= getString(getColumnIndexOrThrow(DatabaseContract.FavColumns.USER_URL))
                val name = getString(getColumnIndexOrThrow(DatabaseContract.FavColumns.USER_NAME))
                favList.add(ModelFav(id, login, userImg, userUrl, name))
            }
        }
        return favList
    }

}