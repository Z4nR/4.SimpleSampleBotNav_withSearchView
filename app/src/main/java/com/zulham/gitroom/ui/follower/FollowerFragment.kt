package com.zulham.gitroom.ui.follower

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zulham.gitroom.R
import com.zulham.gitroom.adapter.FollowAdapter
import com.zulham.gitroom.data.model.ModelFollow
import kotlinx.android.synthetic.main.fragment_follower.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class FollowerFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private lateinit var followerViewModel: FollowerViewModel

    companion object{

        const val USERNAME = "username"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_follower, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString(USERNAME)

        showLoading(true)

        followerViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowerViewModel::class.java)
        username?.let { followerViewModel.setFollower(it) }

        followerViewModel.getIsError().observe(viewLifecycleOwner, {
            when (it) {
                true -> showErrorMessage()
                else -> showFollower()
            }
        })

    }

    private fun showLoading(b: Boolean) {
        if (b){
            followerProgressBar.visibility = View.VISIBLE
        } else {
            followerProgressBar.visibility = View.GONE
        }
    }

    private fun recycleV(followers: ArrayList<ModelFollow>) {
        rv_Follower.apply {
            val followerAdapter = FollowAdapter(followers)

            adapter = followerAdapter

            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        }
    }

    private fun showFollower() {
        followerViewModel.getFollower().observe(viewLifecycleOwner, {
            recycleV(it)

            showLoading(false)
        })
    }

    private fun showErrorMessage() {
        followerViewModel.getErrorMessage().observe(viewLifecycleOwner, {
            Toast.makeText(view?.context, it, Toast.LENGTH_LONG).show()
        })
    }

}