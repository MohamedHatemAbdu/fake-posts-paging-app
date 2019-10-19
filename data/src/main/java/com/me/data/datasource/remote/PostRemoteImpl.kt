package com.me.data.datasource

import com.me.data.api.PostsApi
import com.me.data.entities.PostEntity
import com.me.data.entities.mapToDomain
import io.reactivex.Flowable

class PostRemoteImpl constructor(private val api: PostsApi) : PostRemoteDataSource {

    override fun getPosts(page: Int, limit: Int): Flowable<List<PostEntity>> =
        api.getPosts(page, limit).map {
            it.mapToDomain()
        }

    override fun getPost(postId: Long): Flowable<PostEntity> =
        api.getPost(postId).map { it.mapToDomain() }
}