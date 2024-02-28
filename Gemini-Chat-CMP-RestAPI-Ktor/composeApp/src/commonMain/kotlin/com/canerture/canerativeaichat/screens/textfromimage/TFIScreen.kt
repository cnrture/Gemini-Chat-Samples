package com.canerture.canerativeaichat.screens.textfromimage

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.canerture.canerativeaichat.MR
import com.canerture.canerativeaichat.components.BottomArea
import com.canerture.canerativeaichat.components.ChatItem
import com.canerture.canerativeaichat.components.TopBar
import com.canerture.canerativeaichat.data.model.Message
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.launch

class TFIScreen : Screen {
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow

        val screenModel: TFTAIScreenModel = getScreenModel()

        val state by screenModel.state.collectAsState()

        val coroutineScope = rememberCoroutineScope()
        val listState = rememberLazyListState()

        Scaffold(
            topBar = {
                TopBar(
                    title = stringResource(MR.strings.text_from_text_and_image),
                    onBackClick = { navigator.pop() }
                )
            },
            bottomBar = {
                BottomArea.TextFromTextAndImage(
                    onSendClick = { message, image ->
                        screenModel.onEvent(TFIEvent.SendMessage(message, image))
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
                        painter = painterResource(MR.images.vector_bg),
                        contentDescription = stringResource(MR.strings.gemini_icon),
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
}