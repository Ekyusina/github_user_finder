package com.example.githubuserfinder.ui

import android.net.Uri
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuserfinder.R
import com.example.githubuserfinder.model.Items
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_users.view.*
import java.util.*

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {
    var items: ArrayList<Items?>? = null
        set(items){
            field = items
            notifyDataSetChanged()
        }

    fun addItems(items: ArrayList<Items?>?){
        if (this.items == null) {
            this.items = ArrayList()
        }
        items?.let { this.items?.addAll(it) }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.row_users, null))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItems(position))

    fun getItems(position: Int): Items? = items?.get(position)

    override fun getItemCount(): Int = items?.size ?: 0

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Items?) = with(itemView) {

            textViewName.text = item?.name
            Picasso.get()
                .load(if (TextUtils.isEmpty(item?.avatar)) null else Uri.parse(item?.avatar))
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .fit()
                .centerInside()
                .into(avatar)
        }
    }
}