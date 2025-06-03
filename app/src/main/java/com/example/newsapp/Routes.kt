package com.example.newsapp

import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer

@Serializable

object HomepageScreen

@Serializable

data class DescriptionPage(
    val url: String
)