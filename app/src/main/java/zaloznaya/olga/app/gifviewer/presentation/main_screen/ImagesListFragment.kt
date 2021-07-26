package zaloznaya.olga.app.gifviewer.presentation.main_screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import zaloznaya.olga.app.gifviewer.R

@AndroidEntryPoint
class ImagesListFragment: Fragment(R.layout.fragment_images_list) {

    private val viewModel: ImagesListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getImages()
    }
}