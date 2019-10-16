package com.me.domain.repositories

import com.me.domain.datasource.PostCacheDataSource
import com.me.domain.datasource.PostRemoteDataSource
import com.me.domain.entities.PostEntity
import io.reactivex.Flowable

class PostRepositoryImpl(
    val postCacheDataSource: PostCacheDataSource,
    val postRemoteDataSource: PostRemoteDataSource
) : PostRepository {

    override fun getPosts(refresh: Boolean): Flowable<List<PostEntity>> =
        when (refresh) {

            true -> {
                postRemoteDataSource.getPosts().flatMap {
                    postCacheDataSource.setPosts(it)
                }
            }
            false -> {
                postCacheDataSource.getPosts()
                    .onErrorResumeNext { t: Throwable -> getPosts(true) }
            }
        }

    override fun getPost(postId: String, refresh: Boolean): Flowable<PostEntity> =
        when (refresh) {

            true -> {
                postRemoteDataSource.getPost(postId).flatMap {
                    postCacheDataSource.setPost(it)
                }
            }
            false -> postCacheDataSource.getPost(postId)
                .onErrorResumeNext { t: Throwable ->
                    getPost(postId, true)
                }
        }
}
