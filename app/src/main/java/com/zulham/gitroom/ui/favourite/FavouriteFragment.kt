package com.zulham.gitroom.ui.favourite

import android.content.Intent
import android.database.ContentObserver
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zulham.gitroom.R
import com.zulham.gitroom.adapter.FavouriteAdapter
import com.zulham.gitroom.database.db.DatabaseContract.FavColumns.Companion.CONTENT_URI
import com.zulham.gitroom.database.entity.ModelFav
import com.zulham.gitroom.database.mapping.MappingHelper
import com.zulham.gitroom.ui.detail.DetailActivity
import kotlinx.android.synthetic.main.fragment_favourite.*
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class FavouriteFragment : Fragment() {

    private lateinit var favouriteViewModel: FavouriteViewModel

    private val listFav = ArrayList<ModelFav>()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        favouriteViewModel = ViewModelProvider(this).get(FavouriteViewModel::class.java)
        return inflater.inflate(R.layout.fragment_favourite, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        true.showLoading()

        recycleV()

        favouriteViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FavouriteViewModel::class.java)

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)

        val myObserver = object : ContentObserver(handler){
            override fun onChange(selfChange: Boolean) {
                recycleV()
            }
        }
        requireActivity().contentResolver.registerContentObserver( CONTENT_URI,true, myObserver)

    }

    private fun recycleV() {

        getData()

        rv_ListFav.apply {
            val favAdapter = FavouriteAdapter(listFav)

            adapter = favAdapter

            favAdapter.setOnItemClickCallback(object : FavouriteAdapter.OnItemClickCallback{
                override fun onItemClicked(data: ModelFav) {
                    val intent = Intent(context, DetailActivity::class.java)
                    intent.putExtra("user", data.login)
                    startActivity(intent)
                }
            })

            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        }
    }

    private fun getData(){
        val query = requireActivity().contentResolver.query(CONTENT_URI, null, null, null, null)
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