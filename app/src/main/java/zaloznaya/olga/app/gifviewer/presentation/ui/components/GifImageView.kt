package zaloznaya.olga.app.gifviewer.presentation.ui.components

import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.SubcomposeAsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import zaloznaya.olga.app.gifviewer.R
import zaloznaya.olga.app.gifviewer.domain.model.GifImage

@Composable
fun GifImageView(image: GifImage, onImageClick: (position: Int) -> Unit, index: Int) {
    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .components {
            if (Build.VERSION.SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()

    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(image.urlPreview)
            .placeholder(R.drawable.loading_animation)
            .crossfade(true)
            .build(),
        error = {
            Image(painter = painterResource(id = R.drawable.icon_image), contentDescription = null)
        },
        contentDescription = image.title,
        imageLoader = imageLoader,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .clickable { onImageClick.invoke(index) }
            .padding(4.dp)
            .fillMaxSize()
    )
}