package com.canerture.canerativeaichat.data.source

import dev.shreyaspatil.ai.client.generativeai.GenerativeModel
import dev.shreyaspatil.ai.client.generativeai.type.GenerateContentResponse
import dev.shreyaspatil.ai.client.generativeai.type.PlatformImage
import dev.shreyaspatil.ai.client.generativeai.type.content

class GenAiService(
    textModel: GenerativeModel,
    private val visionModel: GenerativeModel,
) {

    private val textChat = textModel.startChat()

    suspend fun sendMessage(message: String): GenerateContentResponse {
        return textChat.sendMessage(message)
    }

    suspend fun sendMessageWithImage(message: String, images: List<ByteArray>): GenerateContentResponse {
        return visionModel.generateContent(
            content("user") {
                text(message)
                images.forEach { image ->
                    image(PlatformImage(image))
                }
            }
        )
    }
}
