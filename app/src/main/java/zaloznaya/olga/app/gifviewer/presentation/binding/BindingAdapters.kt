package zaloznaya.olga.app.gifviewer.presentation.binding

import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import zaloznaya.olga.app.gifviewer.R
import zaloznaya.olga.app.gifviewer.utils.debounceClick

@BindingAdapter("android:OnClick")
fun bindClickListener(view: View?, listener: View.OnClickListener?) {
    view?.debounceClick {
        listener?.onClick(view)
    }
}

@BindingAdapter("android:invisibleUnless")
fun invisibleUnless(view: View, visibilityData: Boolean?) {
    visibilityData?.let { visible ->
        view.visibility = if (visible) View.VISIBLE else View.INVISIBLE
    }
}

@BindingAdapter("android:imageUrl")
fun ImageView.loadImage(url: String?) {
    url?.let {
        val circularProgressDrawable = CircularProgressDrawable(this.context)
        circularProgressDrawable.strokeWidth = 10f
        circularProgressDrawable.centerRadius = 60f
        circularProgressDrawable.setColorSchemeColors(
            ContextCompat.getColor(this.context, R.color.orange),
            ContextCompat.getColor(this.context, R.color.yellow)
        )
        circularProgressDrawable.start()

        Glide.with(this)
            .load(url)
            .placeholder(circularProgressDrawable)
            .error(R.drawable.icon_image)
            .into(this)
    }
}