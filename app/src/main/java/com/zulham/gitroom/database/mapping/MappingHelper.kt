package com.zulham.gitroom.database.mapping

import android.database.Cursor
import com.zulham.gitroom.database.db.DatabaseContract
import com.zulham.gitroom.database.entity.ModelFav

object MappingHelper {

    fun mapCursorToArrayList(favsCursor: Cursor?): ArrayList<ModelFav> {
        val favsList = ArrayList<ModelFav>()
        favsCursor?.apply {
            while (moveToNext()) {
                val imgUser = getString(getColumnIndexOrThrow(DatabaseContract.FavColumns.IMG_USER))
                val userID = getInt(getColumnIndexOrThrow(DatabaseContract.FavColumns.USER_ID))
                val userName = getString(getColumnIndexOrThrow(DatabaseContract.FavColumns.USER_NAME))
                val userUrl = getString(getColumnIndexOrThrow(DatabaseContract.FavColumns.USER_URL))
                favsList.add(ModelFav(userID, userName, imgUser, userUrl))
            }
        }
        return favsList
    }

}