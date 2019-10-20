package com.me.data.db

import androidx.paging.DataSource
import androidx.room.*
import com.me.data.entities.PostData
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface PostDao {

    @Query("Select * from post")
    fun getAllPostsAsDataSourceFactory(): DataSource.Factory<Int, PostData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAllPosts(articles: List<PostData>)

    @Query("Select * from post")
    fun getAllPosts():  List<PostData>

    @Query("Select * from post where id = :postId")
    fun getPost(postId: Long): Flowable<PostData>

    @Update
    fun updatePost(post: PostData): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun savePost(post: PostData): Completable

    @Query("DELETE  FROM post WHERE post.id = :postId ")
    fun deletePost(postId: Long): Completable

    @Query("DELETE FROM post")
    fun clear()
}