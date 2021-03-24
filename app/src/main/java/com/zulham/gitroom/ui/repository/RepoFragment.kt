package com.zulham.gitroom.ui.repository

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zulham.gitroom.R
import com.zulham.gitroom.adapter.RepoAdapter
import com.zulham.gitroom.data.model.ModelRepo
import kotlinx.android.synthetic.main.fragment_repo.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class RepoFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private lateinit var repoViewModel: RepoViewModel

    companion object {

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
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_repo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString(USERNAME)

        showLoading(true)

        repoViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(RepoViewModel::class.java)
        username?.let { repoViewModel.setRepository(it) }

        repoViewModel.getIsError().observe(viewLifecycleOwner, {
            when (it) {
                true -> showErrorMessage()
                else -> showRepo()
            }
        })

    }

    private fun showLoading(b: Boolean) {
        if (b){
            repoProgressBar.visibility = View.VISIBLE
        } else {
            repoProgressBar.visibility = View.GONE
        }
    }

    private fun recycleV(repository: ArrayList<ModelRepo>) {
        rv_Repo.apply {
            val repoAdapter = RepoAdapter(repository)

            adapter = repoAdapter

            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        }
    }

    private fun showRepo(){
        repoViewModel.getRepository().observe(viewLifecycleOwner, {

            recycleV(it)
            showLoading(false)

        })
    }

    private fun showErrorMessage(){
        repoViewModel.getErrorMessage().observe(viewLifecycleOwner, {
            Toast.makeText(view?.context, it, Toast.LENGTH_LONG).show()
        })
    }

}