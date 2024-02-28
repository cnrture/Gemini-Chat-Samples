package com.canerture.canerativeaichat.di

import com.canerture.canerativeaichat.data.repository.GenAiRepository
import com.canerture.canerativeaichat.data.source.GenAiService
import com.canerture.canerativeaichat.screens.textfromimage.TFTAIScreenModel
import com.canerture.canerativeaichat.screens.textfromtext.TFTScreenModel
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val dataModule = module {

    single<GenAiService> {
        GenAiService()
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
