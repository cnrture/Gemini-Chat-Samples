package com.canerture.canerativeaichat.screens.main

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.canerture.canerativeaichat.MR
import com.canerture.canerativeaichat.screens.textfromimage.TFIScreen
import com.canerture.canerativeaichat.screens.textfromtext.TFTScreen
import com.canerture.canerativeaichat.theme.blue
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

class MainScreen : Screen {
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds,
                painter = painterResource(MR.images.vector_bg),
                contentDescription = stringResource(MR.strings.gemini_icon),
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
                    painter = painterResource(MR.images.ic_gemini),
                    contentDescription = stringResource(MR.strings.gemini_icon),
                )

                Spacer(modifier = Modifier.height(48.dp))

                MainButton(
                    text = stringResource(MR.strings.text_from_text),
                    onClick = {
                        navigator.push(TFTScreen())
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                MainButton(
                    text = stringResource(MR.strings.text_from_text_and_image),
                    onClick = {
                        navigator.push(TFIScreen())
                    }
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
}