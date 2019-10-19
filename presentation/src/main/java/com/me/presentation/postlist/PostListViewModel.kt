package com.me.presentation.postlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.me.data.entities.PostEntity
import com.me.data.usecases.PostUseCase
import com.me.presentation.extenstions.setError
import com.me.presentation.extenstions.setLoading
import com.me.presentation.extenstions.setSuccess
import com.me.presentation.model.Resource
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PostListViewModel constructor(private val postUseCase: PostUseCase) : ViewModel() {


    val posts = MutableLiveData<Resource<PagedList<PostEntity>>>()
//    val networkErrors: MutableLiveData<String> = MutableLiveData()

    private val compositeDisposable = CompositeDisposable()

    fun get() {

        val postResult = postUseCase.getPosts()

        compositeDisposable.add(postResult.data
            .doOnSubscribe {
                posts.setLoading()
            }
            .subscribeOn(Schedulers.io())
            .subscribe {
                if (it.size > 0)
                    posts.setSuccess(it)
            }
        )

        compositeDisposable.add(
            postResult.networkErrors
                .subscribeOn(Schedulers.io())
                .subscribe {
                    posts.setError(it)
                }
        )


    }

    fun refreshPosts() {
        postUseCase.refreshPosts()
    }


    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}