package com.shang.epoxypractice

import android.view.View
import com.airbnb.epoxy.*

@EpoxyModelClass(layout = R.layout.item_loading)
abstract class LoadingEpoxyModel : EpoxyModelWithHolder<LoadingEpoxyModel.Holder>() {

    override fun bind(view: Holder) {
        super.bind(view)
    }

    class Holder : EpoxyHolder() {
        override fun bindView(itemView: View) {

        }
    }
}