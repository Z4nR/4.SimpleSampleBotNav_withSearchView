package com.zulham.gitroom.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zulham.gitroom.R
import com.zulham.gitroom.data.model.ModelRepo
import kotlinx.android.synthetic.main.activity_splash.view.*
import kotlinx.android.synthetic.main.list_repo.view.*

class RepoAdapter(private val repo: ArrayList<ModelRepo>): RecyclerView.Adapter<RepoAdapter.RepoViewHolder>() {

    inner class RepoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(repos: ModelRepo) {
            with(itemView){

                tv_repo.text = repos.name
                tv_pushed.text = repos.push.toString()

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_repo, parent, false)
        return RepoViewHolder(view)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.bind(repo[position])
    }

    override fun getItemCount(): Int = repo.size
}