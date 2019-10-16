package com.me.domain.api

import com.me.domain.entities.PostData
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path

interface PostsApi {

    @GET("posts/")
    fun getPosts(): Flowable<List<PostData>>

    @GET("posts/{id}")
    fun getPost(@Path("id") postId: String): Flowable<PostData>
}


