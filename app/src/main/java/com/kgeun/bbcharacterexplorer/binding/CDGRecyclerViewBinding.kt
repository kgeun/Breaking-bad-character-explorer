package com.kgeun.bbcharacterexplorer.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

object CDGRecyclerViewBinding {
    @JvmStatic
    @BindingAdapter("breedsAdapter")
    fun bindBreedAdapter(view: RecyclerView, adapter: RecyclerView.Adapter<*>?) {
        if (adapter == null) {
            return
        }

        view.adapter = adapter
    }

    @JvmStatic
    @BindingAdapter("galleryAdapter")
    fun bindGalleryAdapter(view: RecyclerView, adapter: RecyclerView.Adapter<*>?) {
        if (adapter == null) {
            return
        }

        val layoutManager = GridLayoutManager(view.context, 2)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position == 0) 2 else 1
            }
        }
        view.layoutManager = layoutManager
        view.adapter = adapter
    }
}