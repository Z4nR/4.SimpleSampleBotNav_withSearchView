package com.zulham.gitroom.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.zulham.gitroom.R
import com.zulham.gitroom.data.model.ModelUser
import kotlinx.android.synthetic.main.list_item.view.*

class UserAdapter(private val list: ArrayList<ModelUser>)
    : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ModelUser)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val w = 1000
        private val h = 1000

        fun bind(user: ModelUser){
            with(itemView){
                Glide.with(itemView.context)
                        .load(user.photo)
                        .apply(RequestOptions()
                                .override(w, h))
                        .into(img_user)

                tv_item_username.text = user.username
                tv_item_country.text = user.location
                tv_item_company.text = user.company

                itemView.setOnClickListener { onItemClickCallback?.onItemClicked(user) }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

}