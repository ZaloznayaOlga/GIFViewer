package zaloznaya.olga.app.gifviewer.presentation.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import zaloznaya.olga.app.gifviewer.domain.model.GifImage

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImagesListView(list: List<GifImage>, cellCount: Int, onImageClick: (position: Int) -> Unit) {

    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        cells = GridCells.Fixed(cellCount),
        contentPadding = PaddingValues(4.dp),
        content = {
            itemsIndexed(items = list) { index, image ->
                GifImageView(image, onImageClick, index)
            }
        })
}