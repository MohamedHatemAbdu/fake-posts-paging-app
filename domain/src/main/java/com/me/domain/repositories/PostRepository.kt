package com.me.domain.repositories

import androidx.paging.PagedList
import com.me.domain.entities.PostEntity
import io.reactivex.Flowable
import io.reactivex.Observable

data class PostResult(val data: Observable<PagedList<PostEntity>>, val networkErrors: Observable<String>)

interface PostRepository {

    fun getPosts(): PostResult

    fun refreshPosts()

    fun getPost(postId: String, refresh: Boolean): Flowable<PostEntity>
}