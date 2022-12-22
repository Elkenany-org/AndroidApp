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

class ClickListener<T>(private val clickListener: (type: T) -> Unit) {
    fun onClick(type: T) {
        clickListener(type)
    }
}