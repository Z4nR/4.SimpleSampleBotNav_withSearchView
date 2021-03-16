package com.zulham.gitroom.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.zulham.gitroom.R
import com.zulham.gitroom.adapter.PagerAdapter
import com.zulham.gitroom.data.model.ModelUserDetail
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    private lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        backHome()

        showLoading(true)

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