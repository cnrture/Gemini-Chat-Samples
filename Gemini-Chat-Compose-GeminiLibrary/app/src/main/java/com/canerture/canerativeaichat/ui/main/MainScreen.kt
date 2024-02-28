package com.canerture.canerativeaichat.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.canerture.canerativeaichat.R
import com.canerture.canerativeaichat.ui.theme.blue

@Composable
fun MainRoute(
    onTextFromTextClick: () -> Unit,
    onTextFromImageClick: () -> Unit,
) {
    MainScreen(
        onTextFromTextClick = onTextFromTextClick,
        onTextFromImageClick = onTextFromImageClick
    )
}

@Composable
fun MainScreen(
    onTextFromTextClick: () -> Unit,
    onTextFromImageClick: () -> Unit
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
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 80.dp),
                contentScale = ContentScale.FillWidth,
                painter = painterResource(R.drawable.ic_gemini),
                contentDescription = stringResource(R.string.gemini_icon),
            )

            Spacer(modifier = Modifier.height(48.dp))

            MainButton(
                text = stringResource(R.string.text_from_text),
                onClick = onTextFromTextClick
            )

            Spacer(modifier = Modifier.height(16.dp))

            MainButton(
                text = stringResource(R.string.text_from_text_and_image),
                onClick = onTextFromImageClick
            )
        }
    }
}

@Composable
fun MainButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 60.dp),
        colors = ButtonDefaults.buttonColors(containerColor = blue)
    ) {
        Text(
            modifier = Modifier.padding(12.dp),
            text = text,
            fontSize = 16.sp,
        )
    }
}