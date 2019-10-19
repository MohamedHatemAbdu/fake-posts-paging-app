package com.me.presentation.postdetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.me.data.usecases.PostUseCase
import com.me.presentation.RxSchedulersOverrideRule
import com.me.presentation.postEntity
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import io.reactivex.Flowable
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class PostDetailsViewModelTest {

    private lateinit var viewModel: PostDetailsViewModel

    private val mockPostUseCase: PostUseCase = mock()


    private val postId = postEntity.id

    private val throwable = Throwable()

    // TODO : what is the benefit of this RxSchedulersOverrideRule ??
    @Rule
    @JvmField
    val rxSchedulersOverrideRule = RxSchedulersOverrideRule()

    // TODO : what is the benefit of this InstantTaskExecutorRule ??
    @Rule
    @JvmField
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        viewModel = PostDetailsViewModel(mockPostUseCase)
    }

    @Test
    fun `get post succeeds`() {

        // given
        whenever(mockPostUseCase.getPost(postId))
            .thenReturn(Flowable.just(postEntity))

        // when
        viewModel.getPost(postId)

        // then
        verify(mockPostUseCase).getPost(postId)
        Assert.assertEquals(postEntity, viewModel.post.value)
    }

    // TODO : Revise Unit tests for getPosts fails

    @Test
    fun `get post fails`() {

        // given
        whenever(mockPostUseCase.getPost(postId))
            .thenReturn(Flowable.error(throwable))

        // when
        viewModel.getPost(postId)

        // then
        verify(mockPostUseCase).getPost(postId)
        Assert.assertEquals(null, viewModel.post.value)
    }


    // TODO : Revise Unit tests for deletPost success

    @Test
    fun `delete post succeeds`() {

        // given
        whenever(mockPostUseCase.deletePost(postId))
            .thenReturn(Completable.complete())

        // when
       viewModel.deletePost(postId)

        // then
        verify(mockPostUseCase).deletePost(postId)

    }

    // TODO : Revise Unit tests for deletPost fails

    @Test
    fun `delete post fails`() {

        // given
        whenever(mockPostUseCase.deletePost(postId))
            .thenReturn(Completable.error(throwable))

        // when
        viewModel.deletePost(postId)

        // then
        verify(mockPostUseCase).deletePost(postId)
    }


}