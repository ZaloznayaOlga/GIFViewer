package zaloznaya.olga.app.gifviewer.presentation.detail_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import zaloznaya.olga.app.gifviewer.R
import zaloznaya.olga.app.gifviewer.databinding.FragmentImageBinding
import zaloznaya.olga.app.gifviewer.domain.model.GifImage
import zaloznaya.olga.app.gifviewer.presentation.adapters.ImageViewPagerAdapter

class ImageFragment: Fragment(R.layout.fragment_image) {

    private val viewModel by viewModel<ImageViewModel>()
    private val args: ImageFragmentArgs by navArgs()
    private val adapter = ImageViewPagerAdapter()
    private lateinit var viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentImageBinding
        .inflate(inflater, container, false)
        .apply {
            lifecycleOwner = this@ImageFragment
            lifecycleScope.launch {
                viewModel.setInputParamsImage(args.images.toList() as ArrayList<GifImage>, args.position)
                initViewPager(vpImages)
            }
            vm = viewModel
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getImagesWithPosition().observe(viewLifecycleOwner) { pair ->
            adapter.setImagesList(pair.first)
            viewPager.setCurrentItem(pair.second, false)
        }

        viewModel.backActionLiveEvent.observe(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
    }

    private fun initViewPager(vp: ViewPager2) {
        viewPager = vp
        vp.adapter = adapter
    }
}