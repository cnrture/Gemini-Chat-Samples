package com.canerture.canerativeaichat.screens.textfromimage

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.canerture.canerativeaichat.data.model.Message
import com.canerture.canerativeaichat.data.repository.GenAiRepository
import com.mohamedrejeb.calf.picker.toImageBitmap
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TFTAIScreenModel(
    private val genAiRepository: GenAiRepository
) : ScreenModel {

    private val _state = MutableStateFlow(TFIState())
    val state: StateFlow<TFIState> = _state.asStateFlow()

    fun onEvent(event: TFIEvent) = screenModelScope.launch {
        when (event) {
            is TFIEvent.SendMessage -> send(event.message, event.image)
        }
    }

    private fun send(message: String, images: List<ByteArray>) = screenModelScope.launch {
        setState {
            copy(
                isLoading = true,
                messageList = updateMessageList(Message.User(message, images.map { it.toImageBitmap() }), true)
            )
        }

        val response = genAiRepository.sendMessageWithImage(message, images)
        setState { copy(isLoading = false, messageList = updateMessageList(response)) }
    }

    private fun setState(reducer: TFIState.() -> TFIState) {
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

data class TFIState(
    val isLoading: Boolean = false,
    val messageList: List<Message> = emptyList()
)

sealed interface TFIEvent {
    data class SendMessage(val message: String, val image: List<ByteArray>) : TFIEvent
}