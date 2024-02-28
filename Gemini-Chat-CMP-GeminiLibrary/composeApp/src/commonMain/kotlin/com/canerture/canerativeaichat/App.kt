package com.canerture.canerativeaichat

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import com.canerture.canerativeaichat.screens.main.MainScreen

@Composable
fun App() {
    MaterialTheme {
        Navigator(MainScreen())
    }
}
