package ru.topbun.ui.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import ru.topbun.ui.Colors
import ru.topbun.ui.R
import ru.topbun.ui.util.rippleClickable

@Composable
fun ChoiceImage(
    modifier: Modifier = Modifier,
    onClickChoiceImage: () -> Unit,
    onDeleteImage: () -> Unit,
    image: Uri?
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Colors.WHITE)
            .border(2.dp, Colors.GRAY, RoundedCornerShape(16.dp))
            .rippleClickable { onClickChoiceImage() },
        contentAlignment = Alignment.Center
    ) {
        image?.let {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = rememberAsyncImagePainter(it),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Icon(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(Colors.BLUE_GRAY.copy(0.2f))
                    .rippleClickable { onDeleteImage() }
                    .padding(8.dp),
                imageVector = Icons.Default.Close,
                contentDescription = null,
                tint = Colors.WHITE
            )
        } ?: Icon(
            modifier = Modifier.size(72.dp),
            painter = painterResource(R.drawable.ic_send_image),
            contentDescription = null,
            tint = Colors.BLUE
        )
    }
}