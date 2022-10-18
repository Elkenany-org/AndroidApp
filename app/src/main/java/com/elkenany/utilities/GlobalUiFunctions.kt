package com.elkenany.utilities

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.elkenany.entities.stock_data.LocalStockBanner

class GlobalUiFunctions {
    companion object {

        // enable image slider to work on all screens
        fun enableImageSlider(
            list: List<LocalStockBanner?>,
            imageSlider: ImageSlider,
            activity: Activity,
        ) {
            if (list.isEmpty()) {
                imageSlider.visibility = View.GONE
            } else {
                val arrayList = ArrayList<SlideModel>()
                list.map { images ->
                    arrayList.add(SlideModel(images!!.image))
                }.toList()
                imageSlider.apply {
                    imageSlider.visibility = View.VISIBLE
                    setImageList(arrayList)
                    setItemClickListener(object : ItemClickListener {
                        override fun onItemSelected(position: Int) {
                            navigateToBroswerIntent(list[position]!!.link, activity)
                        }

                    })
                }
            }
        }

        // send mail to specific email
        fun emailThisEmail(email: String?, activity: Activity) {
            val emailIntent = Intent(Intent.ACTION_SEND)
            emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
            emailIntent.type = "message/rfc822"
            activity.startActivity(Intent.createChooser(emailIntent, "Choose an Email client :"))
        }

        // make a phone call to specific number
        fun callThisNumber(phone: String?, context: Context, activity: Activity) {
            val callIntent = Intent(Intent.ACTION_CALL)
            callIntent.data = Uri.parse("tel:$phone")
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.CALL_PHONE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(Manifest.permission.CALL_PHONE), 1
                )
            } else {
                activity.startActivity(callIntent)
            }

        }

        // navigate to gps to locate a specific location
        fun locateThisLocation(latid: String, longtid: String, activity: Activity) {
            val gmmIntentUri = Uri.parse("google.navigation:q=${latid},${longtid}")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            activity.startActivity(mapIntent)
        }

        // navigate to browser with url
        fun navigateToBroswerIntent(url: String?, activity: Activity) {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            activity.startActivity(intent)
        }

        // share data with anything else
        fun onsharingdata(shareLink: String, activity: Activity) {
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(
                    Intent.EXTRA_TEXT,
                    shareLink
                )
            }
            activity.startActivity(shareIntent)
        }

        // go to google play
        fun navigateToGooglePlay(packageName: String?, activity: Activity) {
            val uri: Uri = Uri.parse("market://details?id=$packageName")
            val goToMarket = Intent(Intent.ACTION_VIEW, uri)
            goToMarket.addFlags(
                Intent.FLAG_ACTIVITY_NO_HISTORY or
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK
            )
            try {
                activity.startActivity(goToMarket)
            } catch (e: ActivityNotFoundException) {
                activity.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=$packageName")
                    )
                )
            }
        }
    }

}