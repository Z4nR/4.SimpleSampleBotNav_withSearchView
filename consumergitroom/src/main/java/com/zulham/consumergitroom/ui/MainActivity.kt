package com.zulham.consumergitroom.ui

import android.database.ContentObserver
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zulham.consumergitroom.R
import com.zulham.consumergitroom.adapter.FavouriteAdapter
import com.zulham.consumergitroom.db.database.DatabaseContract.FavColumns.Companion.CONTENT_URI
import com.zulham.consumergitroom.db.entity.ModelFav
import com.zulham.consumergitroom.db.mapping.MappingHelper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val listFav = ArrayList<ModelFav>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        true.showLoading()

        recycleV()

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)

        val myObserver = object : ContentObserver(handler){
            override fun onChange(selfChange: Boolean) {
                recycleV()
            }
        }
        contentResolver.registerContentObserver( CONTENT_URI,true, myObserver)

    }

    private fun recycleV() {
        getData()

        rv_ListFav.apply {
            val favAdapter = FavouriteAdapter(listFav)

            adapter = favAdapter

            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        }
    }

    private fun getData() {
        val query = contentResolver.query(CONTENT_URI, null, null, null, null)
        val mapping = MappingHelper.mapCursorToArrayList(query)

        if (query != null) {
            if (query.count > 0){

                favProgressBar.visibility = View.GONE

                listFav.addAll(mapping)

            }
        }
    }

    private fun Boolean.showLoading() {
        if (this) {
            favProgressBar.visibility = View.VISIBLE
        } else {
            favProgressBar.visibility = View.GONE
        }
    }
}