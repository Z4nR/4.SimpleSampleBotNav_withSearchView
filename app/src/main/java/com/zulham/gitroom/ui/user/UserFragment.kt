package com.zulham.gitroom.ui.user

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zulham.gitroom.R
import com.zulham.gitroom.adapter.UserAdapter
import com.zulham.gitroom.data.model.ModelUser
import com.zulham.gitroom.ui.detail.DetailActivity
import kotlinx.android.synthetic.main.fragment_user.*

class UserFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        userViewModel =
                ViewModelProvider(this).get(UserViewModel::class.java)
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        search()

        showLoading(true)

        userViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(UserViewModel::class.java)

        userViewModel.setData("Z4nR")

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
                Toast.makeText(context, "Sorry, your search is unfound", Toast.LENGTH_SHORT).show()
                showLoading(true)
                userViewModel.setData(query.toString())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun recycleV(users: ArrayList<ModelUser>) {
        rv_List.apply {
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
        userViewModel.getErrorMessage().observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        })
    }
}