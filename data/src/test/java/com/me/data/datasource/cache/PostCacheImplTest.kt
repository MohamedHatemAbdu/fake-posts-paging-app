package com.me.data.datasource.cache

import com.me.data.db.AppDatabase
import com.me.data.db.PostDao
import com.me.data.entities.mapToDomain
import com.me.data.postData
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test

class PostCacheImplTest {

    private lateinit var dataSource: PostCacheImpl

    private var mockedDatabase: AppDatabase = mock()
    private var mockedPostDao: PostDao = mock()

    private val postId = postData.id

    private val cacheItem = postData.copy(title = "cached")
    private val cacheList = listOf(cacheItem)


    private val throwable = Throwable()

    @Before
    fun setUp() {
        whenever(mockedDatabase.getPostsDao()).thenReturn(mockedPostDao)
        dataSource = PostCacheImpl(mockedDatabase)
    }


    @Test
    fun `get post cache success`() {
        // given
        whenever(mockedPostDao.getPost(postId)).thenReturn(Flowable.just(cacheItem))

        // when
        val test = dataSource.getPost(postId).test()

        // then
        verify(mockedPostDao).getPost(postId)
        test.assertValue(cacheItem.mapToDomain())
    }

    @Test
    fun `get post cache fail`() {
        // given
        whenever(mockedPostDao.getPost(postId)).thenReturn(Flowable.error(throwable))

        // when
        val test = dataSource.getPost(postId).test()

        // then
        verify(mockedPostDao).getPost(postId)
        test.assertError(throwable)
    }


    @Test
    fun `set posts cache success`() {
        // given

        // when
         dataSource.setPosts(cacheList.mapToDomain())

        // then
        verify(mockedPostDao).saveAllPosts(cacheList)
    }


    @Test
    fun `set post cache success`() {
        // given
        whenever(mockedPostDao.updatePost(cacheItem)).thenReturn(Completable.complete())

        // when
        val test = dataSource.setPost(cacheItem.mapToDomain()).test()

        // then
        verify(mockedPostDao).updatePost(cacheItem)
        test.assertComplete()
        test.assertNoErrors()
    }

    @Test
    fun `set post cache fail`() {
        // given
        whenever(mockedPostDao.updatePost(cacheItem)).thenReturn(Completable.error(throwable))

        // when
        val test = dataSource.setPost(cacheItem.mapToDomain()).test()

        // then
        verify(mockedPostDao).updatePost(cacheItem)
        test.assertError(throwable)
    }

    @Test
    fun `add post cache success`() {
        // given
        whenever(mockedPostDao.savePost(cacheItem)).thenReturn(Completable.complete())

        // when
        val test = dataSource.addPost(cacheItem.mapToDomain()).test()

        // then
        verify(mockedPostDao).savePost(cacheItem)
        test.assertComplete()
        test.assertNoErrors()
    }

    @Test
    fun `add post cache fail`() {
        // given
        whenever(mockedPostDao.savePost(cacheItem)).thenReturn(Completable.error(throwable))

        // when
        val test = dataSource.addPost(cacheItem.mapToDomain()).test()

        // then
        verify(mockedPostDao).savePost(cacheItem)
        test.assertError(throwable)
    }


    @Test
    fun `delete post cache success`() {
        // given
        whenever(mockedPostDao.deletePost(postId)).thenReturn(Completable.complete())

        // when
        val test = dataSource.deletePost(postId).test()

        // then
        verify(mockedPostDao).deletePost(postId)
        test.assertComplete()
        test.assertNoErrors()
    }

    @Test
    fun `delete post cache fail`() {
        // given
        whenever(mockedPostDao.deletePost(postId)).thenReturn(Completable.error(throwable))

        // when
        val test = dataSource.deletePost(postId).test()

        // then
        verify(mockedPostDao).deletePost(postId)
        test.assertError(throwable)
    }

}