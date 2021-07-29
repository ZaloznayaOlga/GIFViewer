package zaloznaya.olga.app.gifviewer.presentation.detail_screen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import zaloznaya.olga.app.gifviewer.R
import zaloznaya.olga.app.gifviewer.databinding.FragmentImageBinding
import zaloznaya.olga.app.gifviewer.presentation.adapters.ImageViewPagerAdapter
import zaloznaya.olga.app.gifviewer.utils.TAG

@AndroidEntryPoint
class ImageFragment: Fragment(R.layout.fragment_image) {

    private val viewModel: ImageViewModel by viewModels()
    private val args: ImageFragmentArgs by navArgs()
    private val adapter = ImageViewPagerAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentImageBinding
        .inflate(inflater, container, false)
        .apply {
            lifecycleOwner = this@ImageFragment
            lifecycleScope.launch {
                viewModel.setInputParamsImage(args.image)
                initViewPager(vpImages)
            }
            vm = viewModel
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getImages().observe(viewLifecycleOwner) { list ->
            Log.d(TAG, "list size = ${list.size}")
            adapter.setImagesList(list)
        }

        viewModel.backActionLiveEvent.observe(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
    }

    private fun initViewPager(vp: ViewPager2) {
        vp.adapter = adapter
    }
}