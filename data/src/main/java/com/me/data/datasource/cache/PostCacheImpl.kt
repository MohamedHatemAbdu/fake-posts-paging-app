package com.me.data.datasource.cache

import androidx.paging.DataSource
import com.me.data.db.AppDatabase
import com.me.data.db.PostDao
import com.me.data.datasource.PostCacheDataSource
import com.me.data.entities.PostEntity
import com.me.data.entities.mapToData
import com.me.data.entities.mapToDomain
import io.reactivex.Completable
import io.reactivex.Flowable

class PostCacheImpl(database: AppDatabase) : PostCacheDataSource {

    private val dao: PostDao = database.getPostsDao()

    override fun getPosts(): DataSource.Factory<Int, PostEntity> {
        return dao.getAllPostsAsDataSourceFactory().map { it.mapToDomain() }
    }

    override fun setPosts(postsList: List<PostEntity>) {
        dao.saveAllPosts(postsList.mapToData())

    }

    override fun getPost(postId: Long): Flowable<PostEntity> {
        return dao.getPost(postId).map {
            it.mapToDomain()
        }
    }

    override fun addPost(post: PostEntity): Completable =
        dao.savePost(post.mapToData())

    override fun setPost(post: PostEntity): Completable =
        dao.updatePost(post.mapToData())


    override fun deletePost(postId: Long): Completable =
        dao.deletePost(postId)


}