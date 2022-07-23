package com.example.elkenany

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun ImageView.loadImage(url: String?) {
    if (url.isNullOrEmpty()) {
        this.visibility = View.GONE
    } else {
        Glide.with(this)
            .load(url)
            .centerCrop()
            .into(this)
    }
}

@BindingAdapter("itemText")
fun TextView.writeText(text: String?) {
    if (text!!.isEmpty()) {
        this.visibility = View.GONE
    } else {
        this.text = text
    }
}

//Generic ClickListener
class ClickListener<T>(private val clickListener: (type: T) -> Unit) {
    fun onClick(type: T) {
        clickListener(type)
    }
}