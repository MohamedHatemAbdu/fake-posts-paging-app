package com.me.presentation.model

import com.me.domain.entities.PostEntity


data class PostItem(
    val id: String,
    val title: String,
    val body: String

)

fun PostEntity.mapToPresentation(): PostItem = PostItem(
    id, title, body
)

fun List<PostEntity>.mapToPresentation(): List<PostItem> = map { it.mapToPresentation() }