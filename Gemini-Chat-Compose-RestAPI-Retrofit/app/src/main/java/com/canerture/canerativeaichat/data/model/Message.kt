package com.canerture.canerativeaichat.data.model

import androidx.compose.ui.graphics.ImageBitmap

sealed interface Message {

    data object Loading : Message

    data class User(
        val text: String,
        val images: List<ImageBitmap> = emptyList(),
    ) : Message

    data class Model(
        val text: String,
    ) : Message

    data class Error(
        val text: String,
    ) : Message
}