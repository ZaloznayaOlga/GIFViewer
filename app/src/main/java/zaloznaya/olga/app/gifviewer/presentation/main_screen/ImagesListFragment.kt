package zaloznaya.olga.app.gifviewer.presentation.main_screen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import zaloznaya.olga.app.gifviewer.R
import zaloznaya.olga.app.gifviewer.databinding.FragmentImagesListBinding
import zaloznaya.olga.app.gifviewer.presentation.adapters.ImagesListAdapter
import zaloznaya.olga.app.gifviewer.utils.TAG

@AndroidEntryPoint
class ImagesListFragment: Fragment(R.layout.fragment_images_list) {

    private val viewModel: ImagesListViewModel by viewModels()
    private val adapter = ImagesListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentImagesListBinding
        .inflate(inflater, container, false)
        .apply {
            lifecycleOwner = this@ImagesListFragment
            lifecycleScope.launch {
                rvImages.adapter = adapter
                rvImages.layoutManager = GridLayoutManager(requireContext(), 3)
                // add paging for rv
            }
            viewmodel = viewModel
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter.setViewModel(viewModel)
        viewModel.getImages().observe(viewLifecycleOwner) { list ->
            Log.d(TAG, "size = ${list.size} | Print LIST:")
            list.forEach {
                Log.d(TAG, it.toString())
            }
            adapter.setImagesList(list)
        }
    }
}