package com.canerture.canerativeaichat.data.source

import android.graphics.Bitmap
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.GenerateContentResponse
import com.google.ai.client.generativeai.type.content

class GenAiService(
    textModel: GenerativeModel,
    private val visionModel: GenerativeModel,
) {

    private val textChat = textModel.startChat()

    suspend fun sendMessage(message: String): GenerateContentResponse {
        return textChat.sendMessage(message)
    }

    suspend fun sendMessageWithImage(message: String, images: List<Bitmap>): GenerateContentResponse {
        return visionModel.generateContent(
            content("user") {
                text(message)
                images.forEach { image ->
                    image(image)
                }
            }
        )
    }
}
