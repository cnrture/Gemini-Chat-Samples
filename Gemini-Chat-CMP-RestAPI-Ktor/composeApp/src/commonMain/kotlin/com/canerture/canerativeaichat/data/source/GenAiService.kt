package com.canerture.canerativeaichat.data.source

import com.canerture.canerativeaichat.BuildKonfig
import com.canerture.canerativeaichat.data.model.request.ContentRequest
import com.canerture.canerativeaichat.data.model.request.GenerateContentRequest
import com.canerture.canerativeaichat.data.model.request.InlineData
import com.canerture.canerativeaichat.data.model.request.PartRequest
import com.canerture.canerativeaichat.data.model.response.GenerateContentResponse
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class GenAiService : KtorApi() {

    suspend fun sendMessage(message: String): GenerateContentResponse {
        val part = PartRequest(message)
        val contents = ContentRequest(listOf(part))
        val request = GenerateContentRequest(contents)

        return client.post(TEXT_MODEL) {
            url { parameters.append(KEY, BuildKonfig.GEMINI_API_KEY) }
            setBody(request)
        }.body<GenerateContentResponse>()
    }

    @OptIn(ExperimentalEncodingApi::class)
    suspend fun sendMessageWithImage(message: String, images: List<ByteArray>): GenerateContentResponse {
        val textPart = PartRequest(message)
        val imageParts = images.map {
            PartRequest(null, InlineData("image/jpeg", Base64.encode(it)))
        }
        val contents = ContentRequest(listOf(textPart, *imageParts.toTypedArray()))
        val request = GenerateContentRequest(contents)

        return client.post(IMAGE_MODEL) {
            url { parameters.append(KEY, BuildKonfig.GEMINI_API_KEY) }
            setBody(request)
        }.body<GenerateContentResponse>()
    }

    companion object {
        private const val KEY = "key"
        private const val BASE_URL = "https://generativelanguage.googleapis.com/v1beta/models"
        private const val TEXT_MODEL = "$BASE_URL/gemini-pro:generateContent"
        private const val IMAGE_MODEL = "$BASE_URL/gemini-pro-vision:generateContent"
    }
}
