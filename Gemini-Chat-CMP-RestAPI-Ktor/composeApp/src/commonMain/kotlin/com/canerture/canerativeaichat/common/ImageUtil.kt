package com.canerture.canerativeaichat.common

import androidx.compose.runtime.Composable

@Composable
expect fun PickImage(onImageSelected: (List<ByteArray>?) -> Unit)