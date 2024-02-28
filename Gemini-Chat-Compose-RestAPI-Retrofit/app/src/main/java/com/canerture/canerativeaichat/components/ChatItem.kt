package com.canerture.canerativeaichat.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Error
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.canerture.canerativeaichat.R
import com.canerture.canerativeaichat.data.model.Message
import com.canerture.canerativeaichat.ui.theme.black
import com.canerture.canerativeaichat.ui.theme.blueLight
import com.canerture.canerativeaichat.ui.theme.white
import kotlinx.coroutines.delay

@Composable
fun ChatItem(message: Message) {

    val isUser = message is Message.User

    val backgroundColor = if (isUser) blueLight else white

    val shape = RoundedCornerShape(
        topStart = if (isUser) 20.dp else 4.dp,
        topEnd = if (isUser) 4.dp else 20.dp,
        bottomStart = 20.dp,
        bottomEnd = 20.dp,
    )

    val horizontalAlignment = if (isUser) Alignment.CenterEnd else Alignment.CenterStart

    Box(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
    ) {
        Card(
            modifier = Modifier.align(horizontalAlignment),
            colors = CardDefaults.cardColors(backgroundColor),
            shape = shape,
        ) {
            when (message) {

                Message.Loading -> {
                    var dotsIndex by remember { mutableIntStateOf(0) }
                    val dots = listOf(".", "..", "...")
                    LaunchedEffect(Unit) {
                        while (true) {
                            delay(500)
                            dotsIndex = (dotsIndex + 1) % dots.size
                        }
                    }
                    ChatBubbleText(stringResource(R.string.loading).plus(dots[dotsIndex]))
                }

                is Message.User -> {
                    if (message.images.isNotEmpty()) {
                        Row(
                            modifier = Modifier.padding(
                                start = 12.dp,
                                top = 12.dp,
                                end = 12.dp
                            ),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            message.images.forEach {
                                Image(
                                    bitmap = it,
                                    contentDescription = stringResource(R.string.selected_image),
                                    modifier = Modifier
                                        .size(96.dp)
                                        .clip(RoundedCornerShape(16.dp)),
                                    contentScale = ContentScale.FillHeight,
                                )
                            }
                        }
                    }
                    ChatBubbleText(message.text)
                }

                is Message.Model -> {
                    ChatBubbleText(message.text)
                }

                is Message.Error -> {
                    ChatBubbleText(message.text, true)
                }
            }
        }
    }
}

@Composable
fun ChatBubbleText(
    message: String,
    isError: Boolean = false
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.padding(12.dp),
            text = message,
            color = black,
        )

        if (isError) {
            Icon(
                modifier = Modifier.padding(end = 8.dp),
                imageVector = Icons.Rounded.Error,
                contentDescription = stringResource(R.string.error),
                tint = Color.Red,
            )
        }
    }
}