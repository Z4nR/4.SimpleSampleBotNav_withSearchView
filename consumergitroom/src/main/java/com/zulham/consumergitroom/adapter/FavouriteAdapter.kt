package com.zulham.consumergitroom.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.zulham.consumergitroom.R
import com.zulham.consumergitroom.db.entity.ModelFav
import kotlinx.android.synthetic.main.listfav.view.*

class FavouriteAdapter(private val favourite: ArrayList<ModelFav>)
    : RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder>() {

    inner class FavouriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val w = 1000
        private val h = 1000

        fun bind(fav: ModelFav){
            with(itemView){
                Glide.with(context)
                        .load(fav.avatar_url)
                        .apply(RequestOptions().override(w, h))
                        .into(img_user)

                tv_item_username.text = fav.login
                tv_item_id.text = fav.id.toString()
                tv_item_url.text = fav.url

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.listfav, parent, false)
        return FavouriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        holder.bind(favourite[position])
    }

    override fun getItemCount(): Int = favourite.size
}