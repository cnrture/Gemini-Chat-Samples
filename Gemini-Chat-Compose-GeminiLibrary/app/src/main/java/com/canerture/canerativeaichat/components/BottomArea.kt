package com.canerture.canerativeaichat.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.material.icons.rounded.Image
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.canerture.canerativeaichat.R
import com.canerture.canerativeaichat.common.PickImage
import com.canerture.canerativeaichat.common.toImageBitmap
import com.canerture.canerativeaichat.ui.theme.black
import com.canerture.canerativeaichat.ui.theme.blue

object BottomArea {
    @Composable
    fun TextFromTextAndImage(
        onSendClick: (String, List<ByteArray>) -> Unit,
    ) {
        var message by rememberSaveable { mutableStateOf("") }
        val selectedImages = remember { mutableStateListOf<ByteArray>() }
        val selectedImagesBitmaps = remember { mutableStateListOf<ImageBitmap>() }
        var showImagePicker by remember { mutableStateOf(false) }

        val keyboardController = LocalSoftwareKeyboardController.current
        val focusManager = LocalFocusManager.current

        if (showImagePicker) {
            PickImage {
                showImagePicker = false
                it?.forEach {
                    selectedImages.add(it)
                    selectedImagesBitmaps.add(it.toImageBitmap())
                }
            }
        }
        Column {
            HorizontalDivider()
            if (selectedImagesBitmaps.isNotEmpty()) {
                SelectedImageList(
                    selectedImages = selectedImagesBitmaps.toList(),
                    onRemoveImageClick = {
                        selectedImages.removeAt(it)
                        selectedImagesBitmaps.removeAt(it)
                    },
                )
            }
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = RoundedCornerShape(100),
                value = message,
                placeholder = { Text(stringResource(R.string.send_message)) },
                onValueChange = { message = it },
                leadingIcon = {
                    ChatIconButton(
                        icon = Icons.Rounded.Image,
                        desc = stringResource(R.string.add_image),
                        tint = blue,
                        onClick = {
                            showImagePicker = true
                        },
                    )
                },
                trailingIcon = {
                    ChatIconButton(
                        icon = Icons.AutoMirrored.Rounded.Send,
                        desc = stringResource(R.string.send_message),
                        tint = blue,
                        onClick = {
                            if (message.isNotBlank() && selectedImages.isNotEmpty()) {
                                onSendClick(message, selectedImages)
                                message = ""
                                selectedImages.clear()
                                selectedImagesBitmaps.clear()
                                keyboardController?.hide()
                                focusManager.clearFocus()
                            }
                        },
                    )
                },
                colors = textFieldColors(),
            )
        }
    }

    @Composable
    fun TextFromText(
        onSendClick: (String) -> Unit,
    ) {
        var message by rememberSaveable { mutableStateOf("") }
        val keyboardController = LocalSoftwareKeyboardController.current
        val focusManager = LocalFocusManager.current

        Column {
            HorizontalDivider()
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = RoundedCornerShape(100),
                value = message,
                placeholder = { Text(stringResource(R.string.send_message)) },
                onValueChange = { message = it },
                trailingIcon = {
                    ChatIconButton(
                        icon = Icons.AutoMirrored.Rounded.Send,
                        desc = stringResource(R.string.send_message),
                        tint = blue,
                        onClick = {
                            if (message.isNotBlank()) {
                                onSendClick(message)
                                message = ""
                                keyboardController?.hide()
                                focusManager.clearFocus()
                            }
                        },
                    )
                },
                colors = textFieldColors(),
            )
        }
    }
}

@Composable
fun SelectedImageList(
    selectedImages: List<ImageBitmap>,
    onRemoveImageClick: (Int) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
    ) {

        selectedImages.forEachIndexed { index, image ->
            Box(
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
            ) {
                Image(
                    modifier = Modifier
                        .size(96.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    bitmap = image,
                    contentScale = ContentScale.Crop,
                    contentDescription = stringResource(R.string.selected_image),
                )
                ChatIconButton(
                    modifier = Modifier
                        .align(Alignment.TopEnd),
                    icon = Icons.Rounded.Cancel,
                    desc = stringResource(R.string.remove_image),
                    tint = Color.Red,
                    onClick = {
                        onRemoveImageClick(index)
                    },
                )
            }
        }
    }
}

@Composable
fun ChatIconButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    desc: String,
    tint: Color,
    onClick: () -> Unit,
) = IconButton(
    modifier = modifier,
    onClick = onClick,
) {
    Icon(
        imageVector = icon,
        contentDescription = desc,
        tint = tint
    )
}

@Composable
fun textFieldColors() = TextFieldDefaults.colors(
    focusedTextColor = black,
    cursorColor = black,
    focusedContainerColor = Color.Transparent,
    unfocusedContainerColor = Color.Transparent,
    focusedIndicatorColor = Color.Transparent,
    unfocusedIndicatorColor = Color.Transparent,
    disabledIndicatorColor = Color.Transparent
)