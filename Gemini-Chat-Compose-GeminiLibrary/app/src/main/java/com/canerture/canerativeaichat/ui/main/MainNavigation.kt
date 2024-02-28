package com.canerture.canerativeaichat.ui.main

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.canerture.canerativeaichat.common.Constants

fun NavGraphBuilder.mainScreen(
    onTextFromTextClick: () -> Unit,
    onTextFromImageClick: () -> Unit
) {
    composable(Constants.Route.mainRoute) {
        MainRoute(
            onTextFromTextClick = onTextFromTextClick,
            onTextFromImageClick = onTextFromImageClick
        )
    }
}