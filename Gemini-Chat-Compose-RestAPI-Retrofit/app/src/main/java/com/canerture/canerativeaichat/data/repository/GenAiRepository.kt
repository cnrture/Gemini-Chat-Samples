package com.canerture.canerativeaichat.data.repository

import com.canerture.canerativeaichat.data.model.Message
import com.canerture.canerativeaichat.data.model.request.ContentRequest
import com.canerture.canerativeaichat.data.model.request.GenerateContentRequest
import com.canerture.canerativeaichat.data.model.request.InlineData
import com.canerture.canerativeaichat.data.model.request.PartRequest
import com.canerture.canerativeaichat.data.model.response.GenerateContentResponse
import com.canerture.canerativeaichat.data.source.GenAiService
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class GenAiRepository(
    private val genAiService: GenAiService,
) {
    suspend fun sendMessage(
        message: String,
    ): Message {
        return try {
            val part = PartRequest(message)
            val contents = ContentRequest(listOf(part))
            val request = GenerateContentRequest(contents)

            val response = genAiService.sendMessage(request)
            if (response.error == null) {
                response.mapToModelMessage()
            } else {
                response.mapToErrorMessage()
            }
        } catch (e: Exception) {
            Message.Error(e.message.orEmpty())
        }
    }

    @OptIn(ExperimentalEncodingApi::class)
    suspend fun sendMessageWithImage(
        message: String,
        images: List<ByteArray>,
    ): Message {
        return try {
            val textPart = PartRequest(message)
            val imageParts = images.map {
                PartRequest(null, InlineData("image/jpeg", Base64.encode(it)))
            }
            val contents = ContentRequest(listOf(textPart, *imageParts.toTypedArray()))
            val request = GenerateContentRequest(contents)

            val response = genAiService.sendMessageWithImage(request)
            if (response.error == null) {
                response.mapToModelMessage()
            } else {
                response.mapToErrorMessage()
            }
        } catch (e: Exception) {
            Message.Error(e.message.orEmpty())
        }
    }

    private fun GenerateContentResponse.mapToModelMessage() = Message.Model(
        text = this.candidates?.first()?.content?.parts?.first()?.text.orEmpty()
    )

    private fun GenerateContentResponse.mapToErrorMessage() = Message.Error(
        text = this.error?.message.orEmpty()
    )
}
