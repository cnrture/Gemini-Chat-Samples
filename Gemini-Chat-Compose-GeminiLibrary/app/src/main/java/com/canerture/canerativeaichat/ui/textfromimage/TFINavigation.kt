package com.canerture.canerativeaichat.ui.textfromimage

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.canerture.canerativeaichat.common.Constants

fun NavGraphBuilder.tfiScreen(
    onBackClick: () -> Unit,
) {
    composable(Constants.Route.textFromImageNavigationRoute) {
        TFIRoute(
            onBackClick = onBackClick
        )
    }
}

fun NavController.navigateTFIScreen(
    navOptions: NavOptions? = null
) {
    this.navigate(
        Constants.Route.textFromImageNavigationRoute,
        navOptions
    )
}