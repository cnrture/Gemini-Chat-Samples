package com.canerture.canerativeaichat.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.canerture.canerativeaichat.common.Constants
import com.canerture.canerativeaichat.ui.main.mainScreen
import com.canerture.canerativeaichat.ui.textfromimage.navigateTFIScreen
import com.canerture.canerativeaichat.ui.textfromimage.tfiScreen
import com.canerture.canerativeaichat.ui.textfromtext.navigateTFTScreen
import com.canerture.canerativeaichat.ui.textfromtext.tftScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Constants.Route.mainRoute
    ) {
        mainScreen(
            onTextFromTextClick = {
                navController.navigateTFTScreen()
            },
            onTextFromImageClick = {
                navController.navigateTFIScreen()
            }
        )

        tftScreen(
            onBackClick = {
                navController.navigateUp()
            }
        )

        tfiScreen(
            onBackClick = {
                navController.navigateUp()
            }
        )
    }
}