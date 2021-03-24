package com.zulham.gitroom.ui.user

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zulham.gitroom.R
import com.zulham.gitroom.adapter.UserAdapter
import com.zulham.gitroom.data.model.ModelUser
import com.zulham.gitroom.ui.detail.DetailActivity
import kotlinx.android.synthetic.main.fragment_user.*
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class UserFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoading(true)

        search()

        userViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(UserViewModel::class.java)

        userViewModel.setData()

        userViewModel.getIsError().observe(viewLifecycleOwner, {

            when(it){
                true -> showErrorMessage()
                else -> showUser()
            }

        })

    }

    private fun showUser(){
        userViewModel.getData().observe(viewLifecycleOwner, {

            recycleV(it)

            showLoading(false)

        })
    }

    private fun search() {
        search_bar.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                search_bar.clearFocus()

                Toast.makeText(context, "this result about $query username", Toast.LENGTH_SHORT).show()

                userViewModel.setSearch(query!!)

                userViewModel.setData()

                showLoading(true)

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun recycleV(users: ArrayList<ModelUser>) {
        rv_ListUser.apply {
            val userAdapter = UserAdapter(users)

            adapter = userAdapter

            userAdapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback{
                override fun onItemClicked(data: ModelUser) {
                    val intent = Intent(context, DetailActivity::class.java)
                    intent.putExtra("user", data.login)
                    startActivity(intent)
                }
            })

            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

    private fun showErrorMessage() {
        userViewModel.getErrorMessage().observe(viewLifecycleOwner, {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        })
    }
}