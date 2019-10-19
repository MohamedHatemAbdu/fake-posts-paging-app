package com.me.presentation.postdetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.me.data.entities.PostEntity
import com.me.data.usecases.PostUseCase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class PostDetailsViewModel(
    private val postUseCase: PostUseCase
) : ViewModel() {

    val post = MutableLiveData<PostEntity>()
    private val compositeDisposable = CompositeDisposable()

    fun getPost(postId: Long) {
        compositeDisposable.add(
            postUseCase.getPost(
                postId
            ).subscribeOn(Schedulers.io())
                .subscribe({ post.postValue(it) }, { })
        )
    }

    fun deletePost(postId: Long) {
        compositeDisposable.add(
            postUseCase.deletePost(
                postId
            ).subscribeOn(Schedulers.io())
                .subscribe({}, {})
        )
    }


    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}