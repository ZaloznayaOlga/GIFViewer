package zaloznaya.olga.app.gifviewer.presentation.main_screen

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import zaloznaya.olga.app.gifviewer.R
import zaloznaya.olga.app.gifviewer.databinding.FragmentImagesListBinding
import zaloznaya.olga.app.gifviewer.presentation.adapters.ImagesListAdapter
import zaloznaya.olga.app.gifviewer.presentation.adapters.PaginationScrollListener
import zaloznaya.olga.app.gifviewer.utils.TAG

@AndroidEntryPoint
class ImagesListFragment : Fragment(R.layout.fragment_images_list) {

    private val viewModel: ImagesListViewModel by viewModels()
    private val adapter = ImagesListAdapter()
    private var isLastPage: Boolean = false
    private var isLoading: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentImagesListBinding
        .inflate(inflater, container, false)
        .apply {
            lifecycleOwner = this@ImagesListFragment
            lifecycleScope.launch {
                initRecyclerView(rvImages)
                initSearchView(searchView)
            }
            viewmodel = viewModel
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter.setViewModel(viewModel)
        viewModel.getImages().observe(viewLifecycleOwner) { list ->
            Log.d(TAG, "LIST from API:")
            list.forEach {
                Log.d(TAG, "image = ${it.id}")
            }
            isLoading = false
            adapter.setImagesList(list)
        }
    }

    private fun initRecyclerView(rv: RecyclerView) {
        val gridLayoutManager =
            if (requireActivity().resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                GridLayoutManager(requireContext(), 3)
            } else {
                GridLayoutManager(requireContext(), 5)
            }

        rv.adapter = adapter
        rv.layoutManager = gridLayoutManager
        rv.addOnScrollListener(object : PaginationScrollListener(gridLayoutManager) {

            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                isLoading = true
                viewModel.loadNextPage()
            }
        })
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