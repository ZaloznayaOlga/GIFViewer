package zaloznaya.olga.app.gifviewer.presentation.ui.components

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay
import zaloznaya.olga.app.gifviewer.domain.model.GifImage
import zaloznaya.olga.app.gifviewer.presentation.main_screen.ImagesListFragmentDirections
import zaloznaya.olga.app.gifviewer.presentation.main_screen.ImagesListViewModel
import zaloznaya.olga.app.gifviewer.utils.TAG

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImagesScreen(
    screenOrientation: Int,
    viewModel: ImagesListViewModel,
    navController: NavController
) {
    val count = if (screenOrientation == Configuration.ORIENTATION_PORTRAIT) 3 else 5

    val imagesState = viewModel.getImagesState().observeAsState()
    var refreshing by remember { mutableStateOf(false) }
    LaunchedEffect(refreshing) {
        if (refreshing) {
            delay(2000)
            refreshing = false
        }
    }
    SwipeRefresh(
        state = rememberSwipeRefreshState(refreshing),
        onRefresh = { viewModel.reloadTrendingImages() },
//            indicator = { state, trigger ->
//                GlowIndicator(
//                    swipeRefreshState = state,
//                    refreshTriggerDistance = trigger
//                )
//            }
    ) {
        when (val state = imagesState.value) {
            is State.ErrorState -> ErrorView(text = state.message)
            is State.LoadedState<*> -> {
                val images = state.data.mapNotNull { it as? GifImage }
                ImagesListView(list = images, cellCount = count, onImageClick = { position ->
                    Log.d(TAG, "LIST: OnClick $position - ${images[position].id}")
                    navController.navigate(
                        ImagesListFragmentDirections.actionImagesListFragmentToImageFragment(
                            position, images.toTypedArray()
                        )
                    )
                })
            }
            is State.LoadingState -> LoadingImagesView()
            is State.NoItemsState -> ErrorView(text = "No Images found")
        }
    }
}

@Composable
fun ErrorView(text: String) {
    Box(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = text,
            fontSize = 16.sp,
            color = Color.White
        )
    }
}

@Composable
fun LoadingImagesView() {
    Box(modifier = Modifier.fillMaxSize()
        .verticalScroll(rememberScrollState())) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center),
            color = Color.White
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImagesListView(list: List<GifImage>, cellCount: Int, onImageClick: (position: Int) -> Unit) {

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {
        val padding = 4.dp * 2
        val cardWidth = (maxWidth / cellCount) - padding

        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            cells = GridCells.Fixed(cellCount),
            contentPadding = PaddingValues(4.dp),
            content = {
                itemsIndexed(items = list) { index, image ->
                    GifImageView(image, onImageClick, index, cardWidth)
                }
            })
    }
}