package com.me.data.api

import com.me.data.entities.PostData
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PostsApi {

    @GET("posts/")
    fun getPosts(
        @Query("_page") page: Int,
        @Query("_limit") limit: Int
    ): Flowable<List<PostData>>

    @GET("posts/{id}")
    fun getPost(@Path("id") postId: Long): Flowable<PostData>
}


