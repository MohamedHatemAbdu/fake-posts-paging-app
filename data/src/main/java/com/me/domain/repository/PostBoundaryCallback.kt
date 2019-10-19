package com.me.domain.repository

import android.util.Log
import androidx.paging.PagedList
import com.me.domain.datasource.PostCacheDataSource
import com.me.domain.datasource.PostRemoteDataSource
import com.me.domain.entities.PostData
import com.me.domain.entities.PostEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class PostBoundaryCallback(
    private val remoteDataSource: PostRemoteDataSource,
    private val cacheDataSource: PostCacheDataSource
) : PagedList.BoundaryCallback<PostEntity>() {

    companion object {
        private const val NETWORK_PAGE_SIZE = 10
    }

    private val compositeDisposable = CompositeDisposable()

    private var lastRequestedPage = 1

    val networkErrors: PublishSubject<String> = PublishSubject.create()


    private var isRequestInProgress = false


    override fun onZeroItemsLoaded() {
        requestAndSaveData()
    }


    override fun onItemAtEndLoaded(itemAtEnd: PostEntity) {
        requestAndSaveData()
    }

    private fun requestAndSaveData() {
        if (isRequestInProgress) return

        isRequestInProgress = true

        // TODO : to know how to dispose here.

        compositeDisposable
            .add(
                remoteDataSource.getPosts(
                    lastRequestedPage,
                    NETWORK_PAGE_SIZE
                ).map {
                    cacheDataSource.setPosts(it)
                }.subscribeOn(Schedulers.io())
                    .subscribe({
                        lastRequestedPage++
                        isRequestInProgress = false
//                        compositeDisposable.dispose()
                    }, {
                        isRequestInProgress = false
                        networkErrors.onNext(it.message!!)
//                        compositeDisposable.dispose()
                    })
            )
    }
}