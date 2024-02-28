package com.canerture.canerativeaichat.di

import com.canerture.canerativeaichat.BuildKonfig
import com.canerture.canerativeaichat.data.repository.GenAiRepository
import com.canerture.canerativeaichat.data.source.GenAiService
import com.canerture.canerativeaichat.screens.textfromtext.TFTScreenModel
import com.canerture.canerativeaichat.screens.textfromimage.TFTAIScreenModel
import dev.shreyaspatil.ai.client.generativeai.GenerativeModel
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val dataModule = module {

    single<GenAiService> {
        GenAiService(
            textModel = GenerativeModel(
                modelName = "gemini-pro",
                apiKey = BuildKonfig.GEMINI_API_KEY,
            ),
            visionModel = GenerativeModel(
                modelName = "gemini-pro-vision",
                apiKey = BuildKonfig.GEMINI_API_KEY,
            ),
        )
    }
    single {
        GenAiRepository(get())
    }
}

val screenModelsModule = module {
    factoryOf(::TFTAIScreenModel)
    factoryOf(::TFTScreenModel)
}

fun initKoin() {
    startKoin {
        modules(
            dataModule,
            screenModelsModule,
        )
    }
}
