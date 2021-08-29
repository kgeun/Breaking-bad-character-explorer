package to.chip.dogsgallery.binding

import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import to.chip.dogsgallery.R
import to.chip.dogsgallery.analytics.CDGAnalytics
import to.chip.dogsgallery.data.model.ui.CDGBreedItem
import to.chip.dogsgallery.utils.CDGUtils
import to.chip.dogsgallery.view.custom.CDGLetterImageView


object CDGViewBinding {
    @JvmStatic
    @BindingAdapter("setDrawableId")
    fun loadImageDrawable(view: ImageView, drawableId: Int) {
        Glide.with(view.context)
            .load(drawableId)
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("setItem")
    fun setLetterImage(view: CDGLetterImageView, item: CDGBreedItem?) {
        if (item == null) {
            return
        }

        view.setLetter(item.name!![0].uppercaseChar())
        view.setOval(false)
        view.setTextColor(R.color.white)
        view.setBackgroundColorNumber(item.thumbnailColor ?: CDGUtils.random17())
    }

    @JvmStatic
    @BindingAdapter("setThumbnailUrl")
    fun setThumbnailUrl(view: ImageView, url: String?) {
        if (url == null) {
            return
        }

        Glide.with(view.context)
            .load(url)
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("setClickInfo")
    fun setClickInfo(view: View, breedName: String?) {
        if (breedName == null) {
            return
        }

        view.setOnClickListener {
            view.context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://en.wikipedia.org/w/index.php?search=$breedName")
                )
            )

            CDGAnalytics.sendClick("ClickBreedInfo_$breedName")
        }
    }
}