package com.canerture.canerativeaichat.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.canerture.canerativeaichat.MR
import com.canerture.canerativeaichat.theme.blue
import com.canerture.canerativeaichat.theme.white
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun TopBar(
    title: String,
    onBackClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = blue),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),
            text = title,
            color = white,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp
        )

        ChatIconButton(
            modifier = Modifier.align(Alignment.CenterStart),
            onClick = onBackClick,
            icon = Icons.Rounded.ArrowBack,
            desc = stringResource(MR.strings.back),
            tint = white
        )
    }
}