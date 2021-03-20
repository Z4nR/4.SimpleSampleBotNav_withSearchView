package com.zulham.gitroom.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.zulham.gitroom.R
import com.zulham.gitroom.data.model.ModelUser
import com.zulham.gitroom.database.entity.ModelFav
import kotlinx.android.synthetic.main.list_item.view.*

class FavouriteAdapter(private val favs: ArrayList<ModelFav>)
    : RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ModelFav)
    }

    inner class FavouriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val w = 1000
        private val h = 1000

        fun bind(fav: ModelFav){
            with(itemView){
                Glide.with(context)
                    .load(fav.avatar_url)
                    .apply(
                        RequestOptions()
                        .override(w, h))
                    .into(img_user)

                tv_item_username.text = fav.login
                tv_item_id.text = fav.id.toString()
                tv_item_url.text = fav.url

                itemView.setOnClickListener { onItemClickCallback?.onItemClicked(fav) }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return FavouriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        holder.bind(favs[position])
    }

    override fun getItemCount(): Int = favs.size
}