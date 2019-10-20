package com.me.data

import com.me.data.DataFactory.Factory.randomLong
import com.me.data.DataFactory.Factory.randomUuid
import com.me.data.entities.PostData

class PostsFactory {
    object Factory {


        fun makePostData(): PostData {
            return PostData(randomLong(), randomUuid(), randomUuid())
        }

        fun makePostData(id: Long): PostData {
            return PostData(id, randomUuid(), randomUuid())
        }


        fun makePostDataList(count: Int): List<PostData> {
            val postDataList = mutableListOf<PostData>()
            repeat(count) {
                postDataList.add(makePostData())
            }
            return postDataList
        }

        fun makePostDataListSameIdsDiffCont(postDataList: List<PostData>): List<PostData> {
            val copiedPostDataList = mutableListOf<PostData>()
            postDataList.map { copiedPostDataList.add(makePostData(it.id)) }
            return copiedPostDataList
        }


    }
}