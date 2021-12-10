package com.shang.epoxypractice

import android.view.View
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder


@EpoxyModelClass(layout = R.layout.item_header)
abstract class HeaderEpoxyModel : EpoxyModelWithHolder<HeaderEpoxyModel.Holder>() {

    @EpoxyAttribute
    var text = ""

    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.textView.text=text
    }

    class Holder : EpoxyHolder() {
        lateinit var textView: TextView
        override fun bindView(itemView: View) {

            textView = itemView.findViewById(R.id.textView)
        }
    }
}