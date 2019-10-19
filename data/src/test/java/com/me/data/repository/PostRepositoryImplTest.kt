package com.me.data.repository

import com.me.data.datasource.PostCacheDataSource
import com.me.data.datasource.PostRemoteDataSource
import com.me.data.postEntity
import com.me.data.repositories.PostRepositoryImpl
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test

class PostRepositoryImplTest {

    private lateinit var repository: PostRepositoryImpl

    private val mockCacheDataSource: PostCacheDataSource = mock()
    private val mockRemoteDataSource: PostRemoteDataSource = mock()
    private val mockDataSourceFactory: PostDataSourceFactory = mock()

    private val postId = postEntity.id

    private val cacheItem = postEntity.copy(title = "cache")

//    private val remoteItem = postEntity.copy(title = "remote")
//    private val cacheList = listOf(cacheItem)
//    private val remoteList = listOf(remoteItem)

    private val cacheThrowable = Throwable()
    private val remoteThrowable = Throwable()

    @Before
    fun setUp() {
        repository = PostRepositoryImpl(mockCacheDataSource, mockRemoteDataSource, mockDataSourceFactory)
    }

    // TODO : Unit tests for getPosts
    // TODO : Unit tests for refresh posts

    @Test
    fun `get post cache success`() {
        // given
        whenever(mockCacheDataSource.getPost(postId)).thenReturn(Flowable.just(cacheItem))

        // when
        val test = repository.getPost(postId).test()

        // then
        verify(mockCacheDataSource).getPost(postId)
        test.assertValue(cacheItem)
    }

    @Test
    fun `get post cache fail`() {

        // given
        whenever(mockCacheDataSource.getPost(postId)).thenReturn(Flowable.error(cacheThrowable))

        // when
        val test = repository.getPost(postId).test()

        // then
        verify(mockCacheDataSource).getPost(postId)
        test.assertError(cacheThrowable)
    }

    @Test
    fun `set post cache success`() {
        // given
        whenever(mockCacheDataSource.setPost(cacheItem)).thenReturn(Completable.complete())

        // when
        val test = repository.setPost(cacheItem).test()

        // then
        verify(mockCacheDataSource).setPost(cacheItem)
        test.assertComplete()
    }

    @Test
    fun `set post cache fail`() {

        // given
        whenever(mockCacheDataSource.setPost(cacheItem)).thenReturn(Completable.error(cacheThrowable))

        // when
        val test = repository.setPost(cacheItem).test()

        // then
        verify(mockCacheDataSource).setPost(cacheItem)
        test.assertError(cacheThrowable)
    }

    @Test
    fun `add post cache success`() {
        // given
        whenever(mockCacheDataSource.addPost(cacheItem)).thenReturn(Completable.complete())

        // when
        val test = repository.addPost(cacheItem).test()

        // then
        verify(mockCacheDataSource).addPost(cacheItem)
        test.assertComplete()
    }

    @Test
    fun `add post cache fail`() {

        // given
        whenever(mockCacheDataSource.addPost(cacheItem)).thenReturn(Completable.error(cacheThrowable))

        // when
        val test = repository.addPost(cacheItem).test()

        // then
        verify(mockCacheDataSource).addPost(cacheItem)
        test.assertError(cacheThrowable)
    }

    @Test
    fun `delete post cache success`() {
        // given
        whenever(mockCacheDataSource.deletePost(postId)).thenReturn(Completable.complete())

        // when
        val test = repository.deletePost(postId).test()

        // then
        verify(mockCacheDataSource).deletePost(postId)
        test.assertComplete()
    }

    @Test
    fun `delete post cache fail`() {

        // given
        whenever(mockCacheDataSource.deletePost(postId)).thenReturn(Completable.error(cacheThrowable))

        // when
        val test = repository.deletePost(postId).test()

        // then
        verify(mockCacheDataSource).deletePost(postId)
        test.assertError(cacheThrowable)
    }

}