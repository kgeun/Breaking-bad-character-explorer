package com.kgeun.bbcharacterexplorer.binding

import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.kgeun.bbcharacterexplorer.analytics.CDGAnalytics


object CDGViewBinding {
    @JvmStatic
    @BindingAdapter("setDrawableId")
    fun loadImageDrawable(view: ImageView, drawableId: Int) {
        Glide.with(view.context)
            .load(drawableId)
            .into(view)
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
    @BindingAdapter("seasonList")
    fun setSeasonList(view: TextView, list: List<Int>?) {
        if (list == null) {
            return
        }

        val strBfr = StringBuffer()
        list.forEach {
            strBfr.append("$it ")
        }
        view.text = strBfr.toString()
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