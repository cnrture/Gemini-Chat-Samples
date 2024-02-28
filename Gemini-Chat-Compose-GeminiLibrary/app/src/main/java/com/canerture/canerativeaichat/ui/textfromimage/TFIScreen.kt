package com.canerture.canerativeaichat.ui.textfromimage

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.canerture.canerativeaichat.R
import com.canerture.canerativeaichat.components.BottomArea
import com.canerture.canerativeaichat.components.ChatItem
import com.canerture.canerativeaichat.components.TopBar
import com.canerture.canerativeaichat.data.model.Message
import kotlinx.coroutines.launch

@Composable
fun TFIRoute(
    onBackClick: () -> Unit,
    viewModel: TFIViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    TFIScreen(
        state = state,
        onBackClick = onBackClick,
        onEvent = viewModel::onEvent,
    )
}

@Composable
fun TFIScreen(
    state: TFIState,
    onBackClick: () -> Unit,
    onEvent: (TFIEvent) -> Unit,
) {

    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.text_from_text_and_image),
                onBackClick = onBackClick
            )
        },
        bottomBar = {
            BottomArea.TextFromTextAndImage(
                onSendClick = { message, image ->
                    onEvent(TFIEvent.SendMessage(message, image))
                    coroutineScope.launch {
                        listState.animateScrollToItem(0)
                    }
                },
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds,
                    painter = painterResource(R.drawable.vector_bg),
                    contentDescription = stringResource(R.string.gemini_icon),
                )
                if (state.messageList.isNotEmpty()) {
                    ChatList(
                        messageList = state.messageList,
                        listState = listState,
                    )
                }
            }
        }
    }
}

@Composable
fun ChatList(
    messageList: List<Message>,
    listState: LazyListState,
) {

    LazyColumn(
        state = listState,
        reverseLayout = true,
        contentPadding = PaddingValues(16.dp),
    ) {
        items(messageList.reversed()) { message ->
            ChatItem(message)
        }
    }
}