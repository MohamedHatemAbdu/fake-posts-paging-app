package com.me.data.datasource.cache

import com.me.data.db.AppDatabase
import com.me.data.db.PostDao
import com.me.domain.datasource.PostCacheDataSource
import com.me.domain.entities.PostEntity
import com.me.domain.entities.mapToData
import com.me.domain.entities.mapToDomain
import io.reactivex.Flowable

class PostCacheImpl(private val database: AppDatabase) : PostCacheDataSource {

    private val dao: PostDao = database.getPostsDao()

    override fun getPosts(): Flowable<List<PostEntity>> {
        return dao.getAllPosts().map {
            if (it.isEmpty()) {
                throw Exception("Empty List")
            }
            it.mapToDomain()
        }
    }

    override fun setPosts(postsList: List<PostEntity>): Flowable<List<PostEntity>> {
        dao.clear()
        dao.saveAllPosts(postsList.mapToData())
        return getPosts()
    }

    override fun getPost(postId: String): Flowable<PostEntity> {
        return dao.getPost(postId).map {
            it.mapToDomain()
        }
    }

    override fun setPost(post: PostEntity): Flowable<PostEntity> {
        dao.savePost(post.mapToData())
        return getPost(post.id)
    }
}