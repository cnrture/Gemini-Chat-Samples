package com.canerture.canerativeaichat.common

object Constants {

    object Route {
        const val mainRoute = "main"
        const val textFromTextNavigationRoute = "textFromText"
        const val textFromImageNavigationRoute = "textFromImage"
    }

    object Endpoints {
        const val TEXT_MODEL = "v1beta/models/gemini-pro:generateContent?key={key}"
        const val IMAGE_MODEL = "v1beta/models/gemini-pro-vision:generateContent?key={key}"
    }
}