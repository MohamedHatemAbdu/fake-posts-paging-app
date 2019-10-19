package com.me.presentation.postlist

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.google.android.material.snackbar.Snackbar
import com.me.domain.entities.PostEntity
import com.me.presentation.R
import com.me.presentation.di.injectFeature
import com.me.presentation.extenstions.startRefreshing
import com.me.presentation.extenstions.stopRefreshing
import com.me.presentation.model.Resource
import com.me.presentation.model.ResourceState
import com.me.presentation.postdetails.PostDetailsActivity
import kotlinx.android.synthetic.main.activity_post_list.*
import org.koin.androidx.viewmodel.ext.viewModel

class PostsListActivity : AppCompatActivity() {


    private val itemClick: (PostEntity) -> Unit =
        {

            PostDetailsActivity.navigateTo(
                this@PostsListActivity,
                PostDetailsActivity::class.java,
                it.id
            )
        }

    private val vm: PostListViewModel by viewModel()
    private val adapter = PostListAdapter(itemClick)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_list)

        injectFeature()

        postsRecyclerView.adapter = adapter

        vm.posts.observe(this, Observer {
            updatePosts(it)
        })
//        vm.networkErrors.observe(this, Observer {
//            Snackbar.make(swipeRefreshLayout, getString(R.string.error), Snackbar.LENGTH_INDEFINITE)
//                .setAction(getString(R.string.retry)) {
//                    vm.refreshPosts()
//                }.show()
//        })

        if (savedInstanceState == null) {
            vm.get()
        }

        swipeRefreshLayout.setOnRefreshListener { vm.refreshPosts() }
    }

    private fun updatePosts(resource: Resource<PagedList<PostEntity>>) {

        when (resource.state) {
            ResourceState.LOADING -> swipeRefreshLayout.startRefreshing()
            ResourceState.SUCCESS -> swipeRefreshLayout.stopRefreshing()
            ResourceState.ERROR -> swipeRefreshLayout.stopRefreshing()
        }
        adapter.submitList(resource.data)
        resource.message?.let {
            Snackbar.make(swipeRefreshLayout, getString(R.string.error), Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.retry)) {
                    vm.refreshPosts()
                }.show()
        }

    }

    private fun showEmptyList(show: Boolean) {
        if (show) {
            emptyList.visibility = View.VISIBLE
            swipeRefreshLayout.visibility = View.GONE
        } else {
            emptyList.visibility = View.GONE
            swipeRefreshLayout.visibility = View.VISIBLE
        }
    }
}
