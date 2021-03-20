package com.zulham.gitroom.database.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.zulham.gitroom.database.db.DatabaseContract.FavColumns.Companion.TABLE_NAME

internal class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object{

        private const val DB_NAME = "dbfavuser"

        private const val DB_VERSION = 1

        private const val SQL_CREATE_TABLE_FAV = "CREATE TABLE $TABLE_NAME" +
                " ${DatabaseContract.FavColumns.USER_NAME} TEXT NOT NULL," +
                " ${DatabaseContract.FavColumns.USER_ID} TEXT NOT NULL," +
                " ${DatabaseContract.FavColumns.IMG_USER} TEXT NOT NULL," +
                " ${DatabaseContract.FavColumns.USER_URL} TEXT NOT NULL," +
                " ${DatabaseContract.FavColumns.IS_FAV} BOOLEAN)"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE_FAV)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

}