package zaloznaya.olga.app.gifviewer.presentation.main_screen

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import zaloznaya.olga.app.gifviewer.R
import zaloznaya.olga.app.gifviewer.presentation.ui.components.ImagesListView
import zaloznaya.olga.app.gifviewer.utils.TAG
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay
import zaloznaya.olga.app.gifviewer.domain.model.GifImage
import zaloznaya.olga.app.gifviewer.presentation.ui.components.GlowIndicator

class ImagesListFragment : Fragment(R.layout.fragment_images_list) {

    private val viewModel by viewModel<ImagesListViewModel>()

    private var isLastPage: Boolean = false
    private var isLoading: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(context = requireContext()).apply {
        setContent {
            ImagesScreen(viewModel = viewModel, findNavController())
        }
    }

//        FragmentImagesListBinding
//        .inflate(inflater, container, false)
//        .apply {
//            lifecycleOwner = this@ImagesListFragment
//            lifecycleScope.launch {
//                initRecyclerView(rvImagesComposeView)
//                initSearchView(searchView)
//            }
//            viewmodel = viewModel
//        }.root

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun ImagesScreen(
        viewModel: ImagesListViewModel,
        navController: NavController
    ) {
        val count = if (requireActivity().resources.configuration.orientation
            == Configuration.ORIENTATION_PORTRAIT
        ) 3 else 5

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

    private fun initSearchView(sv: SearchView) {
        sv.setOnClickListener {
            sv.isIconified = false
            sv.requestFocusFromTouch()
        }
        sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.newSearchImages(query)
                Log.d(TAG, "SEARCH: $query")
                sv.clearFocus()
                return true
            }

            override fun onQueryTextChange(query: String): Boolean {
                if (query.isEmpty()) viewModel.reloadTrendingImages()
                return true
            }
        })
    }
}