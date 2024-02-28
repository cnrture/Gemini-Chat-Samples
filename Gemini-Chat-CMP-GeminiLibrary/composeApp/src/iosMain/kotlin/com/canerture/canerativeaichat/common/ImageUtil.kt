package com.canerture.canerativeaichat.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import com.mohamedrejeb.calf.io.readByteArray
import com.mohamedrejeb.calf.picker.FilePickerFileType
import com.mohamedrejeb.calf.picker.FilePickerSelectionMode
import com.mohamedrejeb.calf.picker.rememberFilePickerLauncher
import kotlinx.cinterop.BetaInteropApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

@OptIn(BetaInteropApi::class)
@Composable
actual fun PickImage(onImageSelected: (List<ByteArray>?) -> Unit) {
    val coroutineScope = rememberCoroutineScope()

    val launcher = rememberFilePickerLauncher(
        type = FilePickerFileType.Image,
        selectionMode = FilePickerSelectionMode.Multiple,
        onResult = { files ->
            coroutineScope.launch(Dispatchers.IO) {
                onImageSelected(
                    files.map {
                        it.readByteArray()
                    }
                )
            }
        },
    )

    LaunchedEffect(Unit) {
        launcher.launch()
    }
}