package com.me.domain.repositories

import androidx.paging.RxPagedListBuilder
import com.me.domain.datasource.PostCacheDataSource
import com.me.domain.datasource.PostRemoteDataSource
import com.me.domain.entities.PostEntity
import com.me.domain.repository.PostBoundaryCallback
import com.me.domain.repository.PostDataSourceFactory
import io.reactivex.Completable
import io.reactivex.Flowable


class PostRepositoryImpl(
    val postCacheDataSource: PostCacheDataSource,
    val postRemoteDataSource: PostRemoteDataSource,
    val dataSourceFactory: PostDataSourceFactory
) : PostRepository {


    override fun getPosts(): PostResult {
        val boundaryCallback = PostBoundaryCallback(postRemoteDataSource, postCacheDataSource)
        val networkErrors = boundaryCallback.networkErrors

        val posts = RxPagedListBuilder(dataSourceFactory, DATABASE_PAGE_SIZE)
            .setBoundaryCallback(boundaryCallback)
            .buildObservable()



        return PostResult(posts, networkErrors)
    }


    override fun getPost(postId: String): Flowable<PostEntity> =
        postRemoteDataSource.getPost(postId)

    override fun setPost(post: PostEntity): Completable =
        postCacheDataSource.setPost(post)

    override fun deletPost(postId: String): Completable =
        postCacheDataSource.deletePost(postId)

    override fun refreshPosts() {
        dataSourceFactory.sourceLiveData.value?.invalidate()
    }


    companion object {
        private const val DATABASE_PAGE_SIZE = 6
    }
}


