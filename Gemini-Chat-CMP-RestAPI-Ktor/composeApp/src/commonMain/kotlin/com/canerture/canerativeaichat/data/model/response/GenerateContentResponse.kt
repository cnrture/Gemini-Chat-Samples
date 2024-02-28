package com.canerture.canerativeaichat.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class GenerateContentResponse(val error: Error? = null, val candidates: List<Candidate>? = null)

@Serializable
data class Candidate(val content: Content? = null)

@Serializable
data class Content(val parts: List<Part>? = null)

@Serializable
data class Part(val text: String? = null)

@Serializable
data class Error(val message: String? = null)