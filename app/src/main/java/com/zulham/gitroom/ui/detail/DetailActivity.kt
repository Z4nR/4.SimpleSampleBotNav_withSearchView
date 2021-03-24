package com.zulham.gitroom.ui.detail

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.zulham.gitroom.R
import com.zulham.gitroom.adapter.PagerAdapter
import com.zulham.gitroom.data.model.ModelUserDetail
import com.zulham.gitroom.database.db.DatabaseContract
import com.zulham.gitroom.database.db.DatabaseContract.FavColumns.Companion.CONTENT_URI
import com.zulham.gitroom.database.db.DatabaseContract.FavColumns.Companion.IMG_USER
import com.zulham.gitroom.database.db.DatabaseContract.FavColumns.Companion.IS_FAV
import com.zulham.gitroom.database.db.DatabaseContract.FavColumns.Companion.LOGIN
import com.zulham.gitroom.database.db.DatabaseContract.FavColumns.Companion.USER_ID
import com.zulham.gitroom.database.db.DatabaseContract.FavColumns.Companion.USER_NAME
import com.zulham.gitroom.database.db.DatabaseContract.FavColumns.Companion.USER_URL
import com.zulham.gitroom.database.db.FavHelper
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class DetailActivity : AppCompatActivity() {

    private lateinit var detailViewModel: DetailViewModel

    private lateinit var favHelper: FavHelper

    private lateinit var contentValues: ContentValues

    private lateinit var fav: FloatingActionButton

    private var queryFav = false
    private var statusFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        backHome()

        showLoading(true)

        fav = findViewById(R.id.fabFav)

        contentValues = ContentValues()

        val sectionsPagerAdapter = PagerAdapter(this, supportFragmentManager)
        vP_List.adapter = sectionsPagerAdapter
        tabLayout.setupWithViewPager(vP_List)

        val user = intent.getStringExtra("user")

        detailViewModel = ViewModelProvider(this@DetailActivity, ViewModelProvider.NewInstanceFactory()).get(DetailViewModel::class.java)

        user.let {
            sectionsPagerAdapter.username = it
            detailViewModel.setDetail(it)
        }

        detailViewModel.getIsError().observe(this, {
            when (it){
                true -> showErrorMessage()
                else -> showDetail()
            }
        })

        favHelper = FavHelper.getInstance(applicationContext)

        setStatusFavorite(statusFavorite, false)
        fav.setOnClickListener{
            statusFavorite = !statusFavorite
            contentValues.put(IS_FAV, statusFavorite)
            contentValues.put(LOGIN, user)

            when (queryFav){
                true -> favHelper.update(contentValues.getAsString(USER_NAME), contentValues)
                false -> contentResolver.insert(CONTENT_URI, contentValues)
            }

            setStatusFavorite(statusFavorite, true)
        }

    }

    private fun showLoading(state: Boolean) {
        if (state) {
            detailProgressBar.visibility = View.VISIBLE
        } else {
            detailProgressBar.visibility = View.GONE
        }
    }

    private fun backHome() {
        supportActionBar?.title = "GitDetail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun detailUser(userDetail: ModelUserDetail) {
        val w = 1000
        val h = 1000

        Glide.with(this@DetailActivity)
                .load(userDetail.avatar_url)
                .apply(RequestOptions().override(w, h))
                .into(img_user)

        tv_name_detail.text = userDetail.name
        tv_username_detail.text = userDetail.login
        tv_company_detail.text = userDetail.company
        tv_country_detail.text = userDetail.location
        tv_repo_detail.text = StringBuilder(userDetail.repository.toString()).append("\n Repository")
        tv_follower_detail.text = StringBuilder(userDetail.follower.toString()).append("\n Follower")
        tv_following_detail.text = StringBuilder(userDetail.following.toString()).append("\n Following")

        val data = userDetail.name?.let { favHelper.queryById(it) }

        queryFav = data!!.count > 0
        if (queryFav){
            data.moveToFirst()
            statusFavorite =  data.getInt(data.getColumnIndex(DatabaseContract.FavColumns.IS_FAV)) == 1
            setStatusFavorite(statusFavorite, false)
        }

        contentValues.put(USER_ID, userDetail.id)
        contentValues.put(USER_NAME, userDetail.name)
        contentValues.put(IMG_USER, userDetail.avatar_url)
        contentValues.put(USER_URL, userDetail.url)

    }

    private fun setStatusFavorite(statusFavorite: Boolean, withToast: Boolean) {
        if(statusFavorite) {
            if (withToast)Toast.makeText(this, "I Choose You, Senpai", Toast.LENGTH_SHORT).show()
            fav.setImageResource(R.drawable.ic_baseline_favorite_24)
         }
        else {
            if (withToast)Toast.makeText(this, "Thanks, Senpai", Toast.LENGTH_SHORT).show()
            fav.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }
    }

    private fun showDetail(){

        detailViewModel.getDetail().observe(this, {

            detailUser(it)
            showLoading(false)

        })

    }

    private fun showErrorMessage(){

        detailViewModel.getErrorMessage().observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })

    }
}