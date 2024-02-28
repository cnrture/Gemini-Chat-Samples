package com.canerture.canerativeaichat.ui.textfromtext

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.canerture.canerativeaichat.common.Constants

fun NavGraphBuilder.tftScreen(
    onBackClick: () -> Unit,
) {
    composable(Constants.Route.textFromTextNavigationRoute) {
        TFTRoute(
            onBackClick = onBackClick
        )
    }
}

fun NavController.navigateTFTScreen(
    navOptions: NavOptions? = null
) {
    this.navigate(
        Constants.Route.textFromTextNavigationRoute,
        navOptions
    )
}