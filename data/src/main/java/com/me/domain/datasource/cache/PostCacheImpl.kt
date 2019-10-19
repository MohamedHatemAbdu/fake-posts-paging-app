package com.me.data.datasource.cache

import androidx.paging.DataSource
import com.me.data.db.AppDatabase
import com.me.data.db.PostDao
import com.me.domain.datasource.PostCacheDataSource
import com.me.domain.entities.PostEntity
import com.me.domain.entities.mapToData
import com.me.domain.entities.mapToDomain
import io.reactivex.Completable
import io.reactivex.Flowable

class PostCacheImpl(database: AppDatabase) : PostCacheDataSource {

    private val dao: PostDao = database.getPostsDao()

    override fun getPosts(): DataSource.Factory<Int, PostEntity> {
        return dao.getAllPosts().map { it.mapToDomain() }
    }

    override fun setPosts(postsList: List<PostEntity>) {
        dao.saveAllPosts(postsList.mapToData())

    }

    override fun getPost(postId: String): Flowable<PostEntity> {
        return dao.getPost(postId).map {
            it.mapToDomain()
        }
    }

    override fun setPost(post: PostEntity): Completable =
        dao.savePost(post.mapToData())


    override fun deletePost(postId: String): Completable =
        dao.deletePost(postId)


}