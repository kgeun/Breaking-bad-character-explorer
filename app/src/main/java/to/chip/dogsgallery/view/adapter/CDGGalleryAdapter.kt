package to.chip.dogsgallery.view.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.findFragment
import androidx.recyclerview.widget.RecyclerView
import to.chip.dogsgallery.analytics.CDGAnalytics
import to.chip.dogsgallery.data.model.ui.CDGImageItem
import to.chip.dogsgallery.databinding.ListitemGalleryBinding
import to.chip.dogsgallery.databinding.ListitemGalleryHeaderBinding
import to.chip.dogsgallery.view.fragment.CDGGalleryFragment

class CDGGalleryAdapter(val parentView: ViewGroup, val imageList: List<CDGImageItem>?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val HEADER = 0
    val CONTENT = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == HEADER) {
            HeaderHolder(
                ListitemGalleryHeaderBinding.inflate(
                    LayoutInflater.from(parentView.context), parentView, false
                )
            )
        } else {
            ImageHolder(
                ListitemGalleryBinding.inflate(
                    LayoutInflater.from(parentView.context), parentView, false
                )
            )
        }
    }

    override fun getItemCount(): Int = imageList?.size ?: 0 + 1

    override fun getItemViewType(position: Int): Int {
        return if (position == 0)
            HEADER
        else
            CONTENT
    }

    inner class ImageHolder(
        private val binding: ListitemGalleryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CDGImageItem) {
            binding.apply {
                imageItem = item
                cardView.isClickable = true
                cardView.isFocusable = true
                cardView.setOnClickListener {
                    binding.root.context.startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(item.thumbnailUrl)
                        )
                    )
                    CDGAnalytics.sendClick("ClickGalleyItem_${item.thumbnailUrl}_${javaClass.simpleName}")
                }
                executePendingBindings()
            }
        }
    }

    inner class HeaderHolder(
        private val binding: ListitemGalleryHeaderBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            binding.apply {
                btnRefresh.setOnClickListener {
                    (binding.root.findFragment<CDGGalleryFragment>()).refreshImageList()
                    CDGAnalytics.sendClick("ClickRefresh_${javaClass.simpleName}")
                }
                executePendingBindings()
            }
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position == 0) {
            (holder as HeaderHolder).bind()
        } else {
            imageList?.let {
                (holder as ImageHolder).bind(imageList[position - 1])
            }
        }
    }
}