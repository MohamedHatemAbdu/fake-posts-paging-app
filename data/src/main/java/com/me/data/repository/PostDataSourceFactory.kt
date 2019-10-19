package com.me.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.me.data.datasource.PostCacheDataSource
import com.me.data.entities.PostEntity

class PostDataSourceFactory (val postCacheDataSource: PostCacheDataSource) :
    DataSource.Factory<Int, PostEntity>() {
    val sourceLiveData = MutableLiveData<DataSource<Int, PostEntity>>()
    lateinit var latestSource: DataSource<Int, PostEntity>

    override fun create(): DataSource<Int, PostEntity> {
        latestSource =  postCacheDataSource.getPosts().create()
        sourceLiveData.postValue(latestSource)
        return latestSource
    }
}