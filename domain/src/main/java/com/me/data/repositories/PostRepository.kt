package com.me.data.repositories

import androidx.paging.PagedList
import com.me.data.entities.PostEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable

data class PostResult(val data: Observable<PagedList<PostEntity>>, val networkErrors: Observable<String>)

interface PostRepository {

    fun getPosts(): PostResult

    fun getPost(postId: String): Flowable<PostEntity>

    fun setPost(post: PostEntity): Completable

    fun deletPost(postId: String): Completable

    fun refreshPosts()

}