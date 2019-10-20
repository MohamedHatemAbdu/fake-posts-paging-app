package com.me.presentation.postadd

import androidx.lifecycle.ViewModel
import com.me.data.entities.PostEntity
import com.me.data.usecases.PostUseCase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class PostAddViewModel(
    private val postUseCase: PostUseCase
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()



    fun addPost(post: PostEntity) {
        compositeDisposable.add(
            postUseCase.addPost(
                post
            ).subscribeOn(Schedulers.io())
                .subscribe({},{})
        )
    }


    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}