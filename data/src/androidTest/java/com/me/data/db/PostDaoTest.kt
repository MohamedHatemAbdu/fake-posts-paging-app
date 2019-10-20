package com.me.data.db

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.runner.AndroidJUnit4
import com.me.data.PostsFactory
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class PostDaoTest {

    private lateinit var postDao: PostDao
    private lateinit var db: AppDatabase

    @Rule
    @JvmField
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()


    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).allowMainThreadQueries().build()
        postDao = db.getPostsDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertPostsThenReturnTheInsertedPosts() {
        val cachedList = PostsFactory.Factory.makePostDataList(5).sortedBy { it.id }

        postDao.saveAllPosts(cachedList)

        val retrievedList = postDao.getAllPosts().sortedBy { it.id }

        assertEquals(retrievedList, cachedList)
    }

    @Test
    fun insertTwoListsWithSameIdsThenReturnTheNewlyInsertedList() {
        val cachedList1 = PostsFactory.Factory.makePostDataList(5).sortedBy { it.id }

        val cachedList2 = PostsFactory.Factory.makePostDataListSameIdsDiffCont(cachedList1).sortedBy { it.id }


        postDao.saveAllPosts(cachedList1)
        postDao.saveAllPosts(cachedList2)

        assertNotEquals(cachedList1, cachedList2)

        val retrievedList = postDao.getAllPosts().sortedBy { it.id }

        assertNotEquals(retrievedList, cachedList1)

        assertEquals(retrievedList, cachedList2)
    }

    @Test
    fun updatePostThenReturnTheUpdatedPost() {
        val cachedItem = PostsFactory.Factory.makePostData()
        val updatedCachedItem = PostsFactory.Factory.makePostData(cachedItem.id)

        postDao.savePost(cachedItem).blockingAwait()
        postDao.updatePost(updatedCachedItem).blockingAwait()

        val test = postDao.getPost(cachedItem.id).test()

        test.assertValue(updatedCachedItem)
        test.assertValueCount(1)
        test.assertNoErrors()
    }

    @Test
    fun removePostThenReturnNotReturnTheRemovedPost() {
        val cachedItem = PostsFactory.Factory.makePostData()

        postDao.savePost(cachedItem).blockingAwait()
        postDao.deletePost(cachedItem.id).blockingAwait()

        val test = postDao.getPost(cachedItem.id).test()

        test.assertNoValues()
        test.assertValueCount(0)
        test.assertNoErrors()
    }
}