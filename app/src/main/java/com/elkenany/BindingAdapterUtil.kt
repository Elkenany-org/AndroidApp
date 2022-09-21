package com.elkenany

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
//import androidx.recyclerview.widget.LinearSmoothScroller
//import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun ImageView.loadImage(url: String?) {
    if (url.isNullOrEmpty()) {
        this.visibility = View.GONE
    } else {
        this.visibility = View.VISIBLE
        Glide.with(this)
            .load(url)
//            .optionalCenterCrop()
            .into(this)
    }
}

@BindingAdapter("itemText")
fun TextView.writeText(text: String?) {
    if (text.isNullOrEmpty()) {
        this.visibility = View.GONE
    } else {
        this.visibility = View.VISIBLE
        this.text = text
    }
}

//Generic ClickListener
class ClickListener<T>(private val clickListener: (type: T) -> Unit) {
    fun onClick(type: T) {
        clickListener(type)
    }
}

//fun RecyclerView.smoothScrollToCenteredPosition(position: Int) {
//    val smoothScroller = object : LinearSmoothScroller(context) {
//
//        override fun calculateDxToMakeVisible(view: View?,
//                                              snapPref: Int): Int {
//            val dxToStart = super.calculateDxToMakeVisible(view, SNAP_TO_START)
//            val dxToEnd = super.calculateDxToMakeVisible(view, SNAP_TO_END)
//
//            return (dxToStart + dxToEnd) / 2
//        }
//    }
//
//    smoothScroller.targetPosition = position
//    layoutManager?.startSmoothScroll(smoothScroller)
//}