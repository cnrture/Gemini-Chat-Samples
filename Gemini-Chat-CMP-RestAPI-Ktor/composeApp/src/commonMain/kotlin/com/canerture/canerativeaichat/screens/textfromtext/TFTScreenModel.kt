package com.canerture.canerativeaichat.screens.textfromtext

import androidx.compose.ui.graphics.ImageBitmap
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.canerture.canerativeaichat.data.model.Message
import com.canerture.canerativeaichat.data.repository.GenAiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TFTScreenModel(
    private val genAiRepository: GenAiRepository
) : ScreenModel {

    private val _state = MutableStateFlow(TFTState())
    val state: StateFlow<TFTState> = _state.asStateFlow()

    fun onEvent(event: TFTEvent) = screenModelScope.launch {
        when (event) {
            is TFTEvent.SendMessage -> send(event.message)
        }
    }

    private fun send(message: String) = screenModelScope.launch {

        setState { copy(messageList = updateMessageList(Message.User(message), true)) }

        val response = genAiRepository.sendMessage(message)
        setState { copy(messageList = updateMessageList(response)) }
    }

    private fun setState(reducer: TFTState.() -> TFTState) {
        _state.update {
            reducer(it)
        }
    }

    private fun updateMessageList(message: Message, isLoading: Boolean = false): List<Message> {
        val oldList = _state.value.messageList
        val newList = oldList.toMutableList()
        newList.add(message)
        if (isLoading) {
            newList.add(Message.Loading)
        } else {
            newList.remove(Message.Loading)
        }
        return newList
    }
}

data class TFTState(
    val selectedImage: ImageBitmap? = null,
    val imagePickerShowState: Boolean = false,
    val messageList: List<Message> = emptyList()
)

sealed interface TFTEvent {
    data class SendMessage(val message: String) : TFTEvent
}