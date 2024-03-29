package com.me.data.repositories

import androidx.paging.RxPagedListBuilder
import com.me.data.datasource.PostCacheDataSource
import com.me.data.datasource.PostRemoteDataSource
import com.me.data.entities.PostEntity
import com.me.data.repository.PostBoundaryCallback
import com.me.data.repository.PostDataSourceFactory
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


    override fun getPost(postId: Long): Flowable<PostEntity> =
        postCacheDataSource.getPost(postId)

    override fun setPost(post: PostEntity): Completable =
        postCacheDataSource.setPost(post)

    override fun addPost(post: PostEntity): Completable =
        postCacheDataSource.addPost(post)

    override fun deletePost(postId: Long): Completable =
        postCacheDataSource.deletePost(postId)

    override fun refreshPosts() {
        dataSourceFactory.sourceLiveData.value?.invalidate()
    }


    companion object {
        private const val DATABASE_PAGE_SIZE = 6
    }
}


