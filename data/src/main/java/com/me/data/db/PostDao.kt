package com.me.data.db

import androidx.paging.DataSource
import androidx.room.*
import com.me.data.entities.PostData
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface PostDao {

    @Query("Select * from post")
    fun getAllPosts(): DataSource.Factory<Int, PostData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAllPosts(articles: List<PostData>)

    @Query("Select * from post where id like :postId")
    fun getPost(postId: String): Flowable<PostData>

    //TODO : why onInsert conflict replace not work properly to ask ?
    @Update
    fun savePost(post: PostData): Completable

    @Query("DELETE  FROM post WHERE post.id = :postId ")
    fun deletePost(postId: String): Completable

    @Query("DELETE FROM post")
    fun clear()
}