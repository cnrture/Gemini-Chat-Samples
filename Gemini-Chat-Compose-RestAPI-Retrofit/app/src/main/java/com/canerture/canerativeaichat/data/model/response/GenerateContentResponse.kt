package com.canerture.canerativeaichat.data.model.response

data class GenerateContentResponse(val error: Error? = null, val candidates: List<Candidate>? = null)

data class Candidate(val content: Content? = null)

data class Content(val parts: List<Part>? = null)

data class Part(val text: String? = null)

data class Error(val message: String? = null)