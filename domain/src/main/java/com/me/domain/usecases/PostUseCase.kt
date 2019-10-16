package com.me.domain.usecases

import com.me.domain.entities.PostEntity
import com.me.domain.repositories.PostRepository
import io.reactivex.Flowable


class PostUseCase constructor(
    private val postRepository: PostRepository
) {

    fun getPosts(refresh: Boolean): Flowable<List<PostEntity>> = postRepository.getPosts(refresh)

    fun getPost(postId: String, refresh: Boolean): Flowable<PostEntity> = postRepository.getPost(postId, refresh)

}


