package com.me.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "post")
data class PostData(
    @PrimaryKey @field:Json(name = "id") val id: String,
    @field:Json(name = "title") val title: String,
    @field:Json(name = "body") val body: String
)

fun PostData.mapToDomain(): PostEntity = PostEntity(id, title, body)
fun List<PostData>.mapToDomain(): List<PostEntity> = map { it.mapToDomain() }

fun PostEntity.mapToData(): PostData = PostData(id, title, body)
fun List<PostEntity>.mapToData(): List<PostData> = map { it.mapToData() }