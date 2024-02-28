package com.canerture.canerativeaichat.ui.textfromtext

import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class TFTViewModel @Inject constructor(
    private val genAiRepository: GenAiRepository
) : ViewModel() {

    private val _state = MutableStateFlow(TFTState())
    val state: StateFlow<TFTState> = _state.asStateFlow()

    fun onEvent(event: TFTEvent) = viewModelScope.launch {
        when (event) {
            is TFTEvent.SendMessage -> send(event.message)
        }
    }

    private fun send(message: String) = viewModelScope.launch {

        setState {
            copy(
                messageList = updateMessageList(
                    message = Message.User(message),
                    isLoading = true
                )
            )
        }

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