package zaloznaya.olga.app.gifviewer.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import zaloznaya.olga.app.gifviewer.R
import zaloznaya.olga.app.gifviewer.databinding.ItemImageBinding
import zaloznaya.olga.app.gifviewer.domain.model.GifImage

class ImageViewPagerAdapter : RecyclerView.Adapter<ImageViewPagerAdapter.ImageViewHolder>() {

    private var imagesList: List<GifImage>? = null

    fun setImagesList(imagesList: List<GifImage>?) {
        this.imagesList = imagesList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemImageBinding =
            DataBindingUtil.inflate(inflater, R.layout.item_image, parent, false)
        return ImageViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return if (imagesList != null) {
            imagesList!!.size
        } else 0
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        imagesList?.get(position)?.let { holder.bind(it) }
    }

    inner class ImageViewHolder(
        private val binding: ItemImageBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(image: GifImage) {
            binding.apply {
                this.image = image
                executePendingBindings()
            }
        }
    }
}