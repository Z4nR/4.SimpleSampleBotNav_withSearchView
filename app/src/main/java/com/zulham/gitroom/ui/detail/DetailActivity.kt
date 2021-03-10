package com.zulham.gitroom.ui.detail

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zulham.gitroom.R
import com.zulham.gitroom.data.model.ModelUser
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        backHome()

        //detailUser()

    }

    private fun backHome() {
        supportActionBar?.title = "GitDetail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    /*@SuppressLint("SetTextI18n")
    private fun detailUser() {
        val user = intent.getParcelableExtra<ModelUser>("user")
        user?.let { img_user.setImageResource(it.photo) }
        tv_name_detail.text = user?.name
        tv_username_detail.text = user?.username
        tv_company_detail.text = user?.company
        tv_country_detail.text = user?.location
        tv_repo_detail.text = user?.repository + "\n Repository"
        tv_follower_detail.text = user?.follower + "\n Follower"
        tv_following_detail.text = user?.following + "\n Following"
    }*/
}