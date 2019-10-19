package com.me.domain.datasource

import androidx.paging.DataSource
import com.me.domain.entities.PostData
import com.me.domain.entities.PostEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface PostCacheDataSource {

    fun getPosts(): DataSource.Factory<Int, PostEntity>

    fun setPosts(postsList: List<PostEntity>)

    fun getPost(postId: String): Flowable<PostEntity>

    fun setPost(post: PostEntity): Flowable<PostEntity>
}

interface PostRemoteDataSource {

    fun getPosts(page: Int, limit: Int): Flowable<List<PostEntity>>

    fun getPost(postId: String): Flowable<PostEntity>
}