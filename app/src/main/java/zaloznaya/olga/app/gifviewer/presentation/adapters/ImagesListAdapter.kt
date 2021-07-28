package zaloznaya.olga.app.gifviewer.presentation.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import zaloznaya.olga.app.gifviewer.R
import zaloznaya.olga.app.gifviewer.databinding.ItemImageBinding
import zaloznaya.olga.app.gifviewer.domain.model.GifImage
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
        val binding: ItemImageBinding =
            DataBindingUtil.inflate(inflater, R.layout.item_image, parent, false)
        return ImagesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImagesViewHolder, position: Int) {
        holder.bind(imagesList?.get(position))
    }

    override fun getItemCount(): Int {
        return if (imagesList != null) {
            imagesList!!.size
        } else 0
    }

    inner class ImagesViewHolder(
        private val binding: ItemImageBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(image: GifImage?) {
            binding.apply {
                this.image = image
                this.vm = viewModel as ImagesListViewModel?
                executePendingBindings()
            }
            binding.ivPreviewGif.setOnClickListener {
                // todo navigate to detail screen
                Log.d(TAG, "LIST: OnClick ${image?.id}")
            }
        }
    }

}