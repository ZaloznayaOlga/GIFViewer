package zaloznaya.olga.app.gifviewer.presentation.ui.components

import android.os.Build
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.SubcomposeAsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import zaloznaya.olga.app.gifviewer.domain.model.GifImage

@Composable
fun GifImageView(image: GifImage, onImageClick: (position: Int) -> Unit, index: Int, cardWith: Dp) {
    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .components {
            if (Build.VERSION.SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()

    // ShimmerAnimation
    val gradient = listOf(
        Color.LightGray.copy(alpha = .1f),
        Color.LightGray.copy(alpha = .6f),
        Color.LightGray.copy(alpha = .1f)
    )
    val cardWidthPx = with(LocalDensity.current) { cardWith.toPx() }
    val gradientWidth: Float = (0.4f * cardWidthPx)
    val transition = rememberInfiniteTransition() // animate infinite times
    val translateAnimation = transition.animateFloat( //animate the transition
        initialValue = 0f,
        targetValue = cardWidthPx + gradientWidth,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1300,
                easing = LinearEasing,
                delayMillis = 300
            ),
            repeatMode = RepeatMode.Restart
        )
    )
    val brush = Brush.linearGradient(
        colors = gradient,
        start = Offset(
            x = translateAnimation.value - gradientWidth,
            y = translateAnimation.value - gradientWidth
        ),
        end = Offset(
            x = translateAnimation.value,
            y = translateAnimation.value
        )
    )

    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(image.urlPreview)
//            .placeholder(R.drawable.loading_animation)
            .crossfade(true)
            .build(),
//        error = {
//            Image(painter = painterResource(id = R.drawable.icon_image), contentDescription = null)
//        },
        contentDescription = image.title,
        imageLoader = imageLoader,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .clickable { onImageClick.invoke(index) }
            .padding(4.dp)
            .fillMaxWidth()
            .height(cardWith)
            .clip(RoundedCornerShape(8.dp))
            .background(brush = brush)
    )
}