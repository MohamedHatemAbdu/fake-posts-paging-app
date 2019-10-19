package com.me.presentation.postedit

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.me.domain.entities.PostEntity
import com.me.presentation.R
import com.me.presentation.di.injectFeature
import com.me.presentation.helpers.Constants
import kotlinx.android.synthetic.main.item_list_post.*
import org.koin.androidx.viewmodel.ext.viewModel


class PostEditActivity : AppCompatActivity() {

    private val vm: PostEditViewModel by viewModel()
    private val postId by lazy { intent.getStringExtra(Constants.POST_ID_KEY) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_edit)

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
        postBody.text = postEntity.body.capitalize()
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.edit_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_validate -> {
            vm.setPost(
                PostEntity(postId, postTitle.text.toString(), postBody.text.toString())
            )
            finish()
            true
        }


        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    companion object {

        fun <T> navigateTo(from: Activity, to: Class<T>, postId: String) {
            val intentToEdit = Intent(from, to)
            intentToEdit.putExtra(Constants.POST_ID_KEY, postId)
            from.startActivity(intentToEdit)
        }
    }
}