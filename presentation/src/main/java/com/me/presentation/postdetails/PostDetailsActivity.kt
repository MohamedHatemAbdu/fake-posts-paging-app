package com.me.presentation.postdetails

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.me.data.entities.PostEntity
import com.me.presentation.R
import com.me.presentation.di.injectFeature
import com.me.presentation.helpers.Constants
import com.me.presentation.postedit.PostEditActivity
import kotlinx.android.synthetic.main.item_list_post.*
import org.koin.androidx.viewmodel.ext.viewModel


class PostDetailsActivity : AppCompatActivity() {

    private val vm: PostDetailsViewModel by viewModel()
    private val postId by lazy { intent.getLongExtra(Constants.POST_ID_KEY, 0) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_details)

        setSupportActionBar(findViewById(R.id.toolbar))

        injectFeature()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""


        if (savedInstanceState == null) {
            vm.getPost(postId)
        }

        vm.post.observe(this, Observer { updatePost(it) })
    }

    private fun updatePost(postEntity: PostEntity) {
        postTitle.text = postEntity.title.capitalize()
        postBody.maxLines = Int.MAX_VALUE
        postBody.text = postEntity.body.capitalize()
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_edit -> {
            PostEditActivity.navigateTo(
                this@PostDetailsActivity,
                PostEditActivity::class.java,
                postId
            )
            finish()
            true
        }

        R.id.action_delete -> {
            finish()
            vm.deletePost(postId)
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    companion object {

        fun <T> navigateTo(from: Activity, to: Class<T>, postId: Long) {
            val intentToDetails = Intent(from, to)
            intentToDetails.putExtra(Constants.POST_ID_KEY, postId)
            from.startActivity(intentToDetails)
        }
    }
}