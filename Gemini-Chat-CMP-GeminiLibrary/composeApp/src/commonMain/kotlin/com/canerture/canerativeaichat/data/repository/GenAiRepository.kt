package com.canerture.canerativeaichat.data.repository

import com.canerture.canerativeaichat.data.model.Message
import com.canerture.canerativeaichat.data.source.GenAiService
import dev.shreyaspatil.ai.client.generativeai.type.GenerateContentResponse

class GenAiRepository(
    private val genAiService: GenAiService,
) {
    suspend fun sendMessage(
        message: String,
    ): Message {
        return try {
            val response = genAiService.sendMessage(message)
            response.mapToModelMessage()
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
            response.mapToModelMessage()
        } catch (e: Exception) {
            Message.Error(e.message.orEmpty())
        }
    }

    private fun GenerateContentResponse.mapToModelMessage() = Message.Model(
        text = this.text.orEmpty()
    )
}
