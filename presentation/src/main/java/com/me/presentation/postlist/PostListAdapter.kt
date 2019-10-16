package com.me.presentation.postlist

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.me.presentation.R
import com.me.presentation.extenstions.inflate
import com.me.presentation.model.PostItem
import kotlinx.android.synthetic.main.item_list_post.view.*

class PostListAdapter constructor(private val itemClick: (PostItem) -> Unit) :
    ListAdapter<PostItem, PostListAdapter.ViewHolder>(PostDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    inner class ViewHolder(parent: ViewGroup) :
        RecyclerView.ViewHolder(parent.inflate(R.layout.item_list_post)) {

        fun bind(item: PostItem) {
            itemView.postTitle.text = item.title.capitalize()
            itemView.postBody.text = item.body.capitalize()
            itemView.setOnClickListener { itemClick.invoke(item) }
        }
    }
}

private class PostDiffCallback : DiffUtil.ItemCallback<PostItem>() {
    override fun areItemsTheSame(oldItem: PostItem, newItem: PostItem): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: PostItem, newItem: PostItem): Boolean =
        oldItem == newItem
}