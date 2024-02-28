package com.canerture.canerativeaichat.data.source

import com.canerture.canerativeaichat.BuildConfig
import com.canerture.canerativeaichat.common.Constants.Endpoints.IMAGE_MODEL
import com.canerture.canerativeaichat.common.Constants.Endpoints.TEXT_MODEL
import com.canerture.canerativeaichat.data.model.request.GenerateContentRequest
import com.canerture.canerativeaichat.data.model.response.GenerateContentResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface GenAiService {

    @POST(TEXT_MODEL)
    suspend fun sendMessage(
        @Body request: GenerateContentRequest,
        @Path("key") key: String = BuildConfig.GEMINI_API_KEY,
    ): GenerateContentResponse

    @POST(IMAGE_MODEL)
    suspend fun sendMessageWithImage(
        @Body request: GenerateContentRequest,
        @Path("key") key: String = BuildConfig.GEMINI_API_KEY,
    ): GenerateContentResponse
}
