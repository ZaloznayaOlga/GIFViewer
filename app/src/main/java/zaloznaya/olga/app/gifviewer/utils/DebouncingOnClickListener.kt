package zaloznaya.olga.app.gifviewer.utils

import android.view.View
import zaloznaya.olga.app.gifviewer.R

class DebouncingOnClickListener(
    private val intervalMillis: Long = 500,
    private val doClick: ((View) -> Unit)
) : View.OnClickListener {

    override fun onClick(v: View) {
        if (enabled) {
            enabled = false
            v.postDelayed(ENABLE_AGAIN, intervalMillis)
            doClick(v)
        }
    }

    companion object {
        @JvmStatic
        var enabled = true
        private val ENABLE_AGAIN =
            Runnable { enabled = true }
    }
}

fun View.allDebounceClick(intervalMillis: Long = 0, doClick: (View) -> Unit) =
    setOnClickListener(DebouncingOnClickListener(intervalMillis = intervalMillis, doClick = doClick))

fun View.debounceClick(debounceTime: Long = 500L, action: (View) -> Unit) {
    setOnClickListener {
        val tag = it.getTag(R.id.click_debounce)
        when {
            tag != null && (tag as Long) > System.currentTimeMillis() -> return@setOnClickListener
            else -> {
                it.setTag(R.id.click_debounce, System.currentTimeMillis() + debounceTime)
                action(it)
            }
        }
    }
}
