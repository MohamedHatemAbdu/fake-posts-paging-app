package com.me.data.usecases

import com.me.data.postEntity
import com.me.data.repositories.PostRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test

class PostUseCaseTest {

    private lateinit var postUseCase: PostUseCase
    private val mockPostRepository: PostRepository = mock()

    private val postsList = listOf(postEntity)

    private val postId = postEntity.id

    val throwable = Throwable()

    @Before
    fun setUp() {
        postUseCase = PostUseCase(mockPostRepository)
    }

    // TODO : Unit tests for getPosts
    // TODO : Unit tests for refresh posts

    @Test
    fun `repository get post success`() {
        // given
        whenever(mockPostRepository.getPost(postId)).thenReturn(Flowable.just(postEntity))

        // when
        val test = postUseCase.getPost(postId).test()

        // test
        verify(mockPostRepository).getPost(postId)

        test.assertNoErrors()
        test.assertComplete()
        test.assertValueCount(1)
        test.assertValue(postEntity)
    }

    @Test
    fun `repository get fail`() {
        // given
        whenever(mockPostRepository.getPost(postId)).thenReturn(Flowable.error(throwable))

        // when
        val test = postUseCase.getPost(postId).test()

        //
        // then
        verify(mockPostRepository).getPost(postId)

        test.assertNoValues()
        test.assertNotComplete()
        test.assertError(throwable)
        test.assertValueCount(0)
    }

    @Test
    fun `repository set post success`() {
        // given
        whenever(mockPostRepository.setPost(postEntity)).thenReturn(Completable.complete())

        // when
        val test = postUseCase.setPost(postEntity).test()

        // test
        verify(mockPostRepository).setPost(postEntity)

        test.assertNoErrors()
        test.assertComplete()
        test.assertValueCount(0)
    }

    @Test
    fun `repository set fail`() {
        // given
        whenever(mockPostRepository.setPost(postEntity)).thenReturn(Completable.error(throwable))

        // when
        val test = postUseCase.setPost(postEntity).test()

        //
        // then
        verify(mockPostRepository).setPost(postEntity)

        test.assertNoValues()
        test.assertNotComplete()
        test.assertError(throwable)
        test.assertValueCount(0)
    }


    @Test
    fun `repository add post success`() {
        // given
        whenever(mockPostRepository.addPost(postEntity)).thenReturn(Completable.complete())

        // when
        val test = postUseCase.addPost(postEntity).test()

        // test
        verify(mockPostRepository).addPost(postEntity)

        test.assertNoErrors()
        test.assertComplete()
        test.assertValueCount(0)
    }

    @Test
    fun `repository addPost fail`() {
        // given
        whenever(mockPostRepository.addPost(postEntity)).thenReturn(Completable.error(throwable))

        // when
        val test = postUseCase.addPost(postEntity).test()

        //
        // then
        verify(mockPostRepository).addPost(postEntity)

        test.assertNoValues()
        test.assertNotComplete()
        test.assertError(throwable)
        test.assertValueCount(0)
    }

    @Test
    fun `repository delete post success`() {
        // given
        whenever(mockPostRepository.deletePost(postId)).thenReturn(Completable.complete())

        // when
        val test = postUseCase.deletePost(postId).test()

        // test
        verify(mockPostRepository).deletePost(postId)

        test.assertNoErrors()
        test.assertComplete()
        test.assertValueCount(0)
    }

    @Test
    fun `repository delete fail`() {
        // given
        whenever(mockPostRepository.deletePost(postId)).thenReturn(Completable.error(throwable))

        // when
        val test = postUseCase.deletePost(postId).test()

        //
        // then
        verify(mockPostRepository).deletePost(postId)

        test.assertNoValues()
        test.assertNotComplete()
        test.assertError(throwable)
        test.assertValueCount(0)
    }
}

