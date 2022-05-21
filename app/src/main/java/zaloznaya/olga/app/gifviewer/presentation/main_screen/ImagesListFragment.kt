package zaloznaya.olga.app.gifviewer.presentation.main_screen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import zaloznaya.olga.app.gifviewer.R
import zaloznaya.olga.app.gifviewer.presentation.ui.components.ImagesScreen
import zaloznaya.olga.app.gifviewer.utils.TAG

class ImagesListFragment : Fragment(R.layout.fragment_images_list) {

    private val viewModel by viewModel<ImagesListViewModel>()

    private var isLastPage: Boolean = false
    private var isLoading: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(context = requireContext()).apply {
        setContent {
            ImagesScreen(screenOrientation = requireActivity().resources.configuration.orientation, viewModel = viewModel, findNavController())
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