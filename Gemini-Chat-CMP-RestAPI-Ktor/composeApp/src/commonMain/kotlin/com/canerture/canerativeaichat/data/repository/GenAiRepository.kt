package com.canerture.canerativeaichat.data.repository

import com.canerture.canerativeaichat.data.model.Message
import com.canerture.canerativeaichat.data.model.response.GenerateContentResponse
import com.canerture.canerativeaichat.data.source.GenAiService

class GenAiRepository(
    private val genAiService: GenAiService,
) {
    suspend fun sendMessage(
        message: String,
    ): Message {
        return try {
            val response = genAiService.sendMessage(message)
            if (response.error == null) {
                response.mapToModelMessage()
            } else {
                response.mapToErrorMessage()
            }
        } catch (e: Exception) {
            Message.Error(e.message.orEmpty())
        }
    }

    suspend fun sendMessageWithImage(
        message: String,
        images: List<ByteArray>,
    ): Message {
        return try {
            val response = genAiService.sendMessageWithImage(message, images)
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
