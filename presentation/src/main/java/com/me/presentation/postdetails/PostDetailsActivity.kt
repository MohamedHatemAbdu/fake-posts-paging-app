package com.me.presentation.postdetails

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.me.presentation.R
import com.me.presentation.di.injectFeature
import com.me.presentation.helpers.Constants
import com.me.presentation.model.PostItem
import kotlinx.android.synthetic.main.item_list_post.*
import org.koin.androidx.viewmodel.ext.viewModel

class PostDetailsActivity : AppCompatActivity() {

    private val vm: PostDetailsViewModel by viewModel()
    private val postId by lazy { intent.getStringExtra(Constants.POST_ID_KEY) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_details)

        injectFeature()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        if (savedInstanceState == null) {
            vm.getPost(postId)
        }

        vm.post.observe(this, Observer { updatePost(it) })
    }

    private fun updatePost(postItem: PostItem?) {
        postItem?.let {
            postTitle.text = it.title.capitalize()
            postBody.maxLines = Int.MAX_VALUE
            postBody.text = it.body.capitalize()
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {

        fun <T> navigateTo(from: Activity, to: Class<T>, postId: String) {
            val intentToDetails = Intent(from, to)
            intentToDetails.putExtra(Constants.POST_ID_KEY, postId)
            from.startActivity(intentToDetails)
        }
    }
}