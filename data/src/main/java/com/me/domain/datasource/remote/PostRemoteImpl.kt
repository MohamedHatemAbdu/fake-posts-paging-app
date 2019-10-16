package com.me.data.datasource

import com.me.domain.api.PostsApi
import com.me.domain.datasource.PostRemoteDataSource
import com.me.domain.entities.PostEntity
import com.me.domain.entities.mapToDomain
import io.reactivex.Flowable

class PostRemoteImpl constructor(private val api: PostsApi) : PostRemoteDataSource {

    override fun getPosts(): Flowable<List<PostEntity>> =
        api.getPosts().map {
            it.mapToDomain()
        }

    override fun getPost(postId: String): Flowable<PostEntity> =
        api.getPost(postId).map { it.mapToDomain() }
}