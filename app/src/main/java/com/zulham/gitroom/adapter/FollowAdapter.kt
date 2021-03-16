package com.zulham.gitroom.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.zulham.gitroom.R
import com.zulham.gitroom.data.model.ModelFollow
import kotlinx.android.synthetic.main.list_item.view.*

class FollowAdapter(private val follower: ArrayList<ModelFollow>) : RecyclerView.Adapter<FollowAdapter.FollowerViewHolder>() {
    inner class FollowerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val w = 1000
        private val h = 1000

        fun bind(user: ModelFollow){
            with(itemView){
                Glide.with(context)
                    .load(user.avatar_url)
                    .apply(
                        RequestOptions()
                            .override(w, h))
                    .into(img_user)

                tv_item_username.text = user.login
                tv_item_id.text = user.id.toString()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_follow, parent, false)
        return FollowerViewHolder(view)
    }

    override fun onBindViewHolder(holder: FollowerViewHolder, position: Int) {
        holder.bind(follower[position])
    }

    override fun getItemCount(): Int = follower.size
}