package zaloznaya.olga.app.gifviewer.presentation.main_screen

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import zaloznaya.olga.app.gifviewer.R
import zaloznaya.olga.app.gifviewer.utils.TAG

@AndroidEntryPoint
class ImagesListFragment: Fragment(R.layout.fragment_images_list) {

    private val viewModel: ImagesListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getImages().observe(viewLifecycleOwner) { list ->
            Log.d(TAG, "size = ${list.size} | Print LIST:")
            list.forEach {
                Log.d(TAG, it.toString())
            }
        }
    }
}