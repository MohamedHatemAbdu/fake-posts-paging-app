package com.me.presentation.postdetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.me.domain.entities.PostEntity
import com.me.domain.usecases.PostUseCase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class PostDetailsViewModel(
    private val postUseCase: PostUseCase
) : ViewModel() {

    val post = MutableLiveData<PostEntity>()
    private val compositeDisposable = CompositeDisposable()

    fun getPost(postId: String) {
        compositeDisposable.add(
            postUseCase.getPost(
                postId, false
            ).subscribeOn(Schedulers.io())
                .subscribe({ post.postValue(it) }, { })
        )
    }


    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}