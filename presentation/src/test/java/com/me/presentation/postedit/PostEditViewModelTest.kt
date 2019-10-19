package com.me.presentation.postdetails

import com.me.data.usecases.PostUseCase
import com.me.presentation.postEntity
import com.me.presentation.postedit.PostEditViewModel
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test

class PostEditViewModelTest {

    private lateinit var viewModel: PostEditViewModel

    private val mockPostUseCase: PostUseCase = mock()


    private val throwable = Throwable()


    @Before
    fun setUp() {
        viewModel = PostEditViewModel(mockPostUseCase)
    }

    // TODO : Revise Unit tests for setPosts success

    @Test
    fun `set post succeeds`() {

        // given
        whenever(mockPostUseCase.setPost(postEntity))
            .thenReturn(Completable.complete())

        // when
        viewModel.setPost(postEntity)

        // then
        verify(mockPostUseCase).setPost(postEntity)
    }

    // TODO : Revise Unit tests for setPosts fails

    @Test
    fun `set post fails`() {

        // given
        whenever(mockPostUseCase.setPost(postEntity))
            .thenReturn(Completable.error(throwable))

        // when
        viewModel.setPost(postEntity)

        // then
        verify(mockPostUseCase).setPost(postEntity)
    }


}