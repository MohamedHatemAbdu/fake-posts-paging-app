package com.me.data.usecases

import com.me.data.entities.PostEntity
import com.me.data.repositories.PostRepository
import com.me.data.repositories.PostResult
import io.reactivex.Completable
import io.reactivex.Flowable


class PostUseCase constructor(
    private val postRepository: PostRepository
) {

    fun getPosts(): PostResult = postRepository.getPosts()

    fun getPost(postId: Long): Flowable<PostEntity> = postRepository.getPost(postId)

    fun setPost(post: PostEntity): Completable =
        postRepository.setPost(post)

    fun addPost(post: PostEntity): Completable =
        postRepository.addPost(post)

    fun deletePost(postId: Long): Completable =
        postRepository.deletePost(postId)

    fun refreshPosts() {
        postRepository.refreshPosts()
    }
}


