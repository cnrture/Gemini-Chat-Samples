package com.canerture.canerativeaichat.data.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenerateContentRequest(val contents: ContentRequest)

@Serializable
data class ContentRequest(val parts: List<PartRequest>)

@Serializable
data class PartRequest(
    val text: String? = null,
    @SerialName("inline_data")
    val inlineData: InlineData? = null,
)

@Serializable
data class InlineData(
    @SerialName("mime_type")
    val mimeType: String,
    val data: String
)