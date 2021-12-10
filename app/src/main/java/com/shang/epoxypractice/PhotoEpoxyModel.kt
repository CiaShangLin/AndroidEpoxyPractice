package com.shang.epoxypractice

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder

@EpoxyModelClass(layout = R.layout.item_photo)
abstract class PhotoEpoxyModel : EpoxyModelWithHolder<PhotoEpoxyModel.Holder>() {

    @EpoxyAttribute
    var position = 0

    @EpoxyAttribute
    var url: String = ""

    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.tvPosition.text = "$position"
    }

    class Holder : EpoxyHolder() {
        lateinit var tvPosition: TextView
        lateinit var imageView: ImageView
        override fun bindView(itemView: View) {
            tvPosition = itemView.findViewById(R.id.tvPosition)
            imageView = itemView.findViewById(R.id.imageView)
        }
    }
}