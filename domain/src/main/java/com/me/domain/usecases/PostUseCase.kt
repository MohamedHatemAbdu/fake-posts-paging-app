package com.me.domain.usecases

import com.me.domain.entities.PostEntity
import com.me.domain.repositories.PostRepository
import com.me.domain.repositories.PostResult
import io.reactivex.Completable
import io.reactivex.Flowable


class PostUseCase constructor(
    private val postRepository: PostRepository
) {

    fun getPosts(): PostResult = postRepository.getPosts()

    fun getPost(postId: String): Flowable<PostEntity> = postRepository.getPost(postId)

    fun setPost(post: PostEntity): Completable =
        postRepository.setPost(post)

    fun deletePost(postId: String): Completable =
        postRepository.deletPost(postId)

    fun refreshPosts() {
        postRepository.refreshPosts()
    }
}


