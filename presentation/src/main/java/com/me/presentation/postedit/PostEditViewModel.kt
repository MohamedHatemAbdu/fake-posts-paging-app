package com.me.presentation.postedit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.me.data.entities.PostEntity
import com.me.data.usecases.PostUseCase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class PostEditViewModel(
    private val postUseCase: PostUseCase
) : ViewModel() {

    val post = MutableLiveData<PostEntity>()
    private val compositeDisposable = CompositeDisposable()

    fun getPost(postId: String) {
        compositeDisposable.add(
            postUseCase.getPost(
                postId
            ).subscribeOn(Schedulers.io())
                .subscribe({ post.postValue(it) }, { })
        )
    }

    fun setPost(post: PostEntity) {
        compositeDisposable.add(
            postUseCase.setPost(
                post
            ).subscribeOn(Schedulers.io())
                .subscribe()
        )
    }


    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}