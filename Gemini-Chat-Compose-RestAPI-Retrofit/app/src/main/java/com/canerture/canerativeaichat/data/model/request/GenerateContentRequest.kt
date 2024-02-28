package com.canerture.canerativeaichat.data.model.request

import com.google.gson.annotations.SerializedName

data class GenerateContentRequest(val contents: ContentRequest)

data class ContentRequest(val parts: List<PartRequest>)

data class PartRequest(
    val text: String? = null,
    @SerializedName("inline_data")
    val inlineData: InlineData? = null,
)

data class InlineData(
    @SerializedName("mime_type")
    val mimeType: String,
    val data: String
)