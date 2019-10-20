package com.me.presentation.postadd

import com.me.data.usecases.PostUseCase
import com.me.presentation.postEntity
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test

class PostAddViewModelTest {

    private lateinit var viewModel: PostAddViewModel

    private val mockPostUseCase: PostUseCase = mock()

    private val throwable = Throwable()


    @Before
    fun setUp() {
        viewModel = PostAddViewModel(mockPostUseCase)
    }


    @Test
    fun `set post succeeds`() {

        // given
        whenever(mockPostUseCase.addPost(postEntity))
            .thenReturn(Completable.complete())

        // when
        viewModel.addPost(postEntity)

        // then
        verify(mockPostUseCase).addPost(postEntity)
    }


    @Test
    fun `set post fails`() {

        // given
        whenever(mockPostUseCase.addPost(postEntity))
            .thenReturn(Completable.error(throwable))

        // when
        viewModel.addPost(postEntity)

        // then
        verify(mockPostUseCase).addPost(postEntity)
    }


}