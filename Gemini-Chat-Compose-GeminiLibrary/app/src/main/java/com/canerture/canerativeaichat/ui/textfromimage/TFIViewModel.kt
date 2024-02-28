package com.canerture.canerativeaichat.ui.textfromimage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.canerture.canerativeaichat.common.toBitmap
import com.canerture.canerativeaichat.common.toImageBitmap
import com.canerture.canerativeaichat.data.model.Message
import com.canerture.canerativeaichat.data.repository.GenAiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TFIViewModel @Inject constructor(
    private val genAiRepository: GenAiRepository
) : ViewModel() {

    private val _state = MutableStateFlow(TFIState())
    val state: StateFlow<TFIState> = _state.asStateFlow()

    fun onEvent(event: TFIEvent) = viewModelScope.launch {
        when (event) {
            is TFIEvent.SendMessage -> send(event.message, event.image)
        }
    }

    private fun send(message: String, images: List<ByteArray>) = viewModelScope.launch {
        setState {
            copy(
                messageList = updateMessageList(
                    message = Message.User(message, images.map { it.toImageBitmap() }),
                    isLoading = true
                )
            )
        }

        val response = genAiRepository.sendMessageWithImage(message, images.map { it.toBitmap() })
        setState { copy(messageList = updateMessageList(response)) }
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
    val messageList: List<Message> = emptyList()
)

sealed interface TFIEvent {
    data class SendMessage(val message: String, val image: List<ByteArray>) : TFIEvent
}