package com.me.domain.repositories

import android.util.Log
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.RxPagedListBuilder
import com.me.domain.datasource.PostCacheDataSource
import com.me.domain.datasource.PostRemoteDataSource
import com.me.domain.repository.PostBoundaryCallback
import com.me.domain.entities.PostEntity
import com.me.domain.repository.PostDataSourceFactory
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers


class PostRepositoryImpl(
    val postCacheDataSource: PostCacheDataSource,
    val postRemoteDataSource: PostRemoteDataSource,
    val dataSourceFactory: PostDataSourceFactory
) : PostRepository {

    override fun getPosts(): PostResult {
//        val dataSourceFactory
//                = PostDataSourceFactory(postCacheDataSource)

        val boundaryCallback = PostBoundaryCallback(postRemoteDataSource, postCacheDataSource)
        val networkErrors = boundaryCallback.networkErrors

        val posts = RxPagedListBuilder(dataSourceFactory, DATABASE_PAGE_SIZE)
            .setBoundaryCallback(boundaryCallback)
            .buildObservable()



        return PostResult(posts, networkErrors)
    }

    override fun refreshPosts() {
        dataSourceFactory.sourceLiveData.value?.invalidate()
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

    companion object {
        private const val DATABASE_PAGE_SIZE = 6
    }
}


