package com.me.data.datasource

import androidx.paging.DataSource
import com.me.data.entities.PostEntity
import io.reactivex.Completable
import io.reactivex.Flowable

interface PostCacheDataSource {

    fun getPosts(): DataSource.Factory<Int, PostEntity>

    fun setPosts(postsList: List<PostEntity>)

    fun getPost(postId: Long): Flowable<PostEntity>

    fun setPost(post: PostEntity): Completable

    fun addPost(post: PostEntity): Completable

    fun deletePost(postId: Long): Completable

}

interface PostRemoteDataSource {

    fun getPosts(page: Int, limit: Int): Flowable<List<PostEntity>>

    fun getPost(postId: Long): Flowable<PostEntity>
}