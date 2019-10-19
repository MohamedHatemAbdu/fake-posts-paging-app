package com.me.presentation.postadd

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
import kotlinx.android.synthetic.main.item_list_post.*
import org.koin.androidx.viewmodel.ext.viewModel


class PostAddActivity : AppCompatActivity() {

    private val vm: PostAddViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_edit)

        setSupportActionBar(findViewById(R.id.toolbar))

        injectFeature()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""


    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.validate_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_validate -> {
            vm.addPost(
                PostEntity(title = postTitle.text.toString(), body = postBody.text.toString())
            )
            finish()
            true
        }


        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    companion object {

        fun <T> navigateTo(from: Activity, to: Class<T>) {
            val intentToEdit = Intent(from, to)
            from.startActivity(intentToEdit)
        }
    }
}