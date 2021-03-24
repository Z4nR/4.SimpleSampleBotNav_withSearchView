package com.zulham.consumergitroom.db.mapping

import android.database.Cursor
import com.zulham.consumergitroom.db.database.DatabaseContract
import com.zulham.consumergitroom.db.entity.ModelFav

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