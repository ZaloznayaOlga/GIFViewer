package zaloznaya.olga.app.gifviewer.presentation.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import zaloznaya.olga.app.gifviewer.R
import zaloznaya.olga.app.gifviewer.databinding.ItemPreviewImageBinding
import zaloznaya.olga.app.gifviewer.domain.model.GifImage
import zaloznaya.olga.app.gifviewer.presentation.main_screen.ImagesListFragmentDirections
import zaloznaya.olga.app.gifviewer.presentation.main_screen.ImagesListViewModel
import zaloznaya.olga.app.gifviewer.utils.TAG

class ImagesListAdapter : RecyclerView.Adapter<ImagesListAdapter.ImagesViewHolder>() {

    private var imagesList: List<GifImage>? = null
    private var viewModel: ViewModel? = null

    fun setImagesList(imagesList: List<GifImage>?) {
        this.imagesList = imagesList
        notifyDataSetChanged()
    }
    fun setViewModel(vm: ViewModel) {
        this.viewModel = vm
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemPreviewImageBinding =
            DataBindingUtil.inflate(inflater, R.layout.item_preview_image, parent, false)
        return ImagesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImagesViewHolder, position: Int) {
        imagesList?.get(position)?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return if (imagesList != null) {
            imagesList!!.size
        } else 0
    }

    inner class ImagesViewHolder(
        private val binding: ItemPreviewImageBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(image: GifImage) {
            binding.apply {
                this.image = image
                this.vm = viewModel as ImagesListViewModel?
                executePendingBindings()
            }
            binding.ivPreviewGif.setOnClickListener {
                Log.d(TAG, "LIST: OnClick ${image?.id}")
                it.findNavController().navigate(ImagesListFragmentDirections.actionImagesListFragmentToImageFragment(image))
            }
        }
    }

}