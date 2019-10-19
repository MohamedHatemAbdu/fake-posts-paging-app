package com.me.domain.usecases

import com.me.domain.entities.PostEntity
import com.me.domain.repositories.PostRepository
import com.me.domain.repositories.PostResult
import io.reactivex.Flowable


class PostUseCase constructor(
    private val postRepository: PostRepository
) {

    fun getPosts(): PostResult = postRepository.getPosts()

    fun refreshPosts() {
        postRepository.refreshPosts()
    }

    fun getPost(postId: String, refresh: Boolean): Flowable<PostEntity> = postRepository.getPost(postId, refresh)

}


