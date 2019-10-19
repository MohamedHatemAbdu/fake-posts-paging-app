package com.me.data.datasource.remote

import com.me.data.ConstValues
import com.me.data.ConstValues.NETWORK_PAGE_SIZE
import com.me.data.ConstValues.PAGE_NUMER
import com.me.data.api.PostsApi
import com.me.data.datasource.PostRemoteImpl
import com.me.data.entities.mapToDomain
import com.me.data.postData
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test

class PostRemoteImplTest {

    private lateinit var dataSource: PostRemoteImpl

    private val mockApi: PostsApi = mock()

    private val postId = postData.id

    private val remoteItem = postData.copy(title = "remote")

    private val remoteList = listOf(remoteItem)

    private val throwable = Throwable()



    @Before
    fun setUp() {
        dataSource = PostRemoteImpl(mockApi)
    }

    @Test
    fun `get posts remote success`() {
        // given
        whenever(mockApi.getPosts(PAGE_NUMER, NETWORK_PAGE_SIZE)).thenReturn(Flowable.just(remoteList))

        // when
        val test = dataSource.getPosts(PAGE_NUMER, NETWORK_PAGE_SIZE).test()

        // then
        verify(mockApi).getPosts(PAGE_NUMER, NETWORK_PAGE_SIZE)
        test.assertValue(remoteList.mapToDomain())
    }

    @Test
    fun `get posts remote fail`() {
        // given
        whenever(mockApi.getPosts(PAGE_NUMER, NETWORK_PAGE_SIZE)).thenReturn(Flowable.error(throwable))

        // when
        val test = dataSource.getPosts(PAGE_NUMER, NETWORK_PAGE_SIZE).test()

        // then
        verify(mockApi).getPosts(PAGE_NUMER, NETWORK_PAGE_SIZE)
        test.assertError(throwable)
    }

    @Test
    fun `get post remote success`() {

        // given
        whenever(mockApi.getPost(postId)).thenReturn(Flowable.just(remoteItem))

        // when
        val test = dataSource.getPost(postId).test()

        // then
        verify(mockApi).getPost(postId)

        test.assertValue(remoteItem.mapToDomain())
    }

    @Test
    fun `get post remote fail`() {

        // given
        whenever(mockApi.getPost(postId)).thenReturn(Flowable.error(throwable))

        // when
        val test = dataSource.getPost(postId).test()

        // then
        verify(mockApi).getPost(postId)
        test.assertError(throwable)
    }
}