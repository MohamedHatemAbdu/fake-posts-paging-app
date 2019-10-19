package com.me.presentation.postlist

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.me.data.entities.PostEntity
import com.me.presentation.R
import com.me.presentation.extenstions.inflate
import kotlinx.android.synthetic.main.item_list_post.view.*

class PostListAdapter constructor(private val itemClick: (PostEntity) -> Unit) :
    PagedListAdapter<PostEntity, PostListAdapter.ViewHolder>(PostDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val postItem = getItem(position)
        holder.bind(postItem)

    }

    inner class ViewHolder(parent: ViewGroup) :
        RecyclerView.ViewHolder(parent.inflate(R.layout.item_list_post)) {

        fun bind(item: PostEntity?) {
            if (item == null) {

                val resources = itemView.context.resources
                itemView.postTitle.text = resources.getString(R.string.loading)
                itemView.postBody.text = resources.getString(R.string.loading)

            } else {
                itemView.postTitle.text = "[${item.id}] ${item.title.capitalize()}"
                itemView.postBody.text = item.body.capitalize()
                itemView.setOnClickListener { itemClick.invoke(item) }
            }
        }
    }
}

private class PostDiffCallback : DiffUtil.ItemCallback<PostEntity>() {
    override fun areItemsTheSame(oldItem: PostEntity, newItem: PostEntity): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: PostEntity, newItem: PostEntity): Boolean =
        oldItem == newItem
}