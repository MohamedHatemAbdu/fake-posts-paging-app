package com.me.data.datasource

import com.me.domain.api.PostsApi
import com.me.domain.datasource.PostRemoteDataSource
import com.me.domain.entities.PostEntity
import com.me.domain.entities.mapToDomain
import io.reactivex.Flowable
import io.reactivex.Single

class PostRemoteImpl constructor(private val api: PostsApi) : PostRemoteDataSource {

    override fun getPosts(page: Int, limit: Int): Flowable<List<PostEntity>> =
        api.getPosts(page, limit).map {
            it.mapToDomain()
        }

    override fun getPost(postId: String): Flowable<PostEntity> =
        api.getPost(postId).map { it.mapToDomain() }
}