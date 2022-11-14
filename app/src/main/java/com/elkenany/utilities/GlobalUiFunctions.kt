package com.elkenany.utilities

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.elkenany.ClickListener
import com.elkenany.R
import com.elkenany.databinding.GeneralFilterLayoutBinding
import com.elkenany.databinding.ImageDialogItemBinding
import com.elkenany.entities.guide.City
import com.elkenany.entities.guide.Country
import com.elkenany.entities.guide.Sector
import com.elkenany.entities.guide.Sort
import com.elkenany.entities.stock_data.GeneralBannerData
import com.google.android.material.bottomsheet.BottomSheetDialog

class GlobalUiFunctions {
    companion object {
        // open image in popup
        fun openPopUpImage(image: String?, activity: Activity?, layoutInflater: LayoutInflater) {
            if (activity != null) {
                if (!image.isNullOrEmpty()) {
                    val dialogBinding = ImageDialogItemBinding.inflate(layoutInflater)
                    dialogBinding.image = image
                    val dialog = Dialog(activity)
                    dialog.setCancelable(true)
                    Log.i("imageUrl", dialogBinding.image.toString())
                    dialog.setContentView(dialogBinding.root)
                    dialog.show()
                }
            }
        }

        // enable image slider to work on all screens
        fun enableImageSlider(
            list: List<GeneralBannerData?>, imageSlider: ImageSlider, activity: Activity,
        ) {
            if (list.isEmpty()) {
                imageSlider.visibility = View.GONE
            } else {
                val arrayList = ArrayList<SlideModel>()
                list.map { images ->
                    arrayList.add(SlideModel(images!!.image))
                }.toList()
                imageSlider.apply {
                    visibility = View.VISIBLE
                    startSliding(3000)
                    setImageList(arrayList, ScaleTypes.FIT)
                    setItemClickListener(object : ItemClickListener {
                        override fun onItemSelected(position: Int) {
                            if (!list[position]!!.link.isNullOrEmpty()) {
                                navigateToBroswerIntent(list[position]!!.link, activity)
                            }
                        }
                    })
                }
            }
        }

        // send mail to specific email
        fun emailThisEmail(email: String?, activity: Activity) {
            if (!email.isNullOrEmpty()) {
                val emailIntent = Intent(Intent.ACTION_SEND)
                emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
                emailIntent.type = "message/rfc822"
                activity.startActivity(
                    Intent.createChooser(
                        emailIntent,
                        "Choose an Email client :"
                    )
                )
            }
        }

        // make a phone call to specific number
        fun callThisNumber(phone: String?, context: Context, activity: Activity) {
            if (!phone.isNullOrEmpty()) {
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
        }

        // navigate to gps to locate a specific location
        fun locateThisLocation(latid: String?, longtid: String?, activity: Activity) {
            if (!latid.isNullOrEmpty() && !longtid.isNullOrEmpty()) {
                val gmmIntentUri = Uri.parse("google.navigation:q=${latid},${longtid}")
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                activity.startActivity(mapIntent)
            }
        }

        // navigate to browser with url
        fun navigateToBroswerIntent(url: String?, activity: Activity) {
            if (!url.isNullOrEmpty()) {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                activity.startActivity(intent)
            }
        }

        // share data with anything else
        fun onsharingdata(shareLink: String?, activity: Activity) {
            if (!shareLink.isNullOrEmpty()) {
                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(
                        Intent.EXTRA_TEXT,
                        shareLink
                    )
                }
                activity.startActivity(shareIntent)
            }
        }

        // go to google play
        fun navigateToGooglePlay(packageName: String?, activity: Activity) {
            if (!packageName.isNullOrEmpty()) {
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

        fun openFilterDialog(
            requireActivity: Activity,
            inflater: LayoutInflater,
            sectionsList: List<Sector?>?,
            sortList: List<Sort?>?,
            countriesList: List<Country?>?,
            citiesList: List<City?>?,
            clickListener: ClickListener<FiltersData>,
        ) {
            val bottomSheetDialog = BottomSheetDialog(requireActivity)
            val binding = GeneralFilterLayoutBinding.inflate(inflater)
            val sectionname = "القسم"
            val sortname = "الترتيب"
            val countryName = "البلد"
            val cityName = "المدينة"
            var section: String? = null
            var sort: String? = null
            var country: String? = null
            var city: String? = null
            binding.apply {
                sectionsAutoCompelete.setText(sectionname)
                sortAutoCompelete.setText(sortname)
                countriesAutoCompelete.setText(countryName)
                citiesAutoComplete.setText(cityName)
                if (sectionsList.isNullOrEmpty()) {
                    sectionsBtn.visibility = View.GONE
                } else {
                    sectionsBtn.visibility = View.VISIBLE
                    val sections =
                        sectionsList.map { newList -> newList!!.name }.toList()
                    val adapter = ArrayAdapter<String?>(
                        requireActivity.applicationContext,
                        R.layout.array_adapter_item,
                        sections
                    )
                    binding.sectionsAutoCompelete.setAdapter(adapter)
                    binding.sectionsAutoCompelete.setOnItemClickListener { adapterView, _, position, _ ->
                        section = sectionsList[position]!!.id.toString()
                        binding.sectionsAutoCompelete.hint = adapterView.getItemAtPosition(position)
                            .toString()
                    }
                }
                if (sortList.isNullOrEmpty()) {
                    sortBtn.visibility = View.GONE
                } else {
                    sortBtn.visibility = View.VISIBLE
                    val sorts =
                        sortList.map { newList -> newList!!.name }.toList()
                    val adapter = ArrayAdapter<String?>(
                        requireActivity.applicationContext,
                        R.layout.array_adapter_item,
                        sorts
                    )
                    binding.sortAutoCompelete.setAdapter(adapter)
                    binding.sortAutoCompelete.setOnItemClickListener { adapterView, _, position, _ ->
                        sort = sortList[position]!!.id.toString()
                        binding.sortAutoCompelete.hint = adapterView.getItemAtPosition(position)
                            .toString()
                    }
                }
                if (countriesList.isNullOrEmpty()) {
                    countriesBtn.visibility = View.GONE
                } else {
                    countriesBtn.visibility = View.VISIBLE
                    val countries =
                        countriesList.map { newList -> newList!!.name }.toList()
                    val adapter = ArrayAdapter<String?>(
                        requireActivity.applicationContext,
                        R.layout.array_adapter_item,
                        countries
                    )
                    binding.countriesAutoCompelete.setAdapter(adapter)
                    binding.countriesAutoCompelete.setOnItemClickListener { adapterView, _, position, _ ->
                        country = countriesList[position]!!.id.toString()
                        binding.countriesAutoCompelete.hint =
                            adapterView.getItemAtPosition(position)
                                .toString()
                    }
                }
                if (citiesList.isNullOrEmpty()) {
                    citiesBtn.visibility = View.GONE
                } else {
                    citiesBtn.visibility = View.VISIBLE
                    val cities =
                        citiesList.map { newList -> newList!!.name }.toList()
                    val adapter = ArrayAdapter<String?>(
                        requireActivity.applicationContext,
                        R.layout.array_adapter_item,
                        cities
                    )
                    binding.citiesAutoComplete.setAdapter(adapter)
                    binding.citiesAutoComplete.setOnItemClickListener { adapterView, _, position, _ ->
                        city = citiesList[position]!!.id.toString()
                        binding.citiesAutoComplete.hint =
                            adapterView.getItemAtPosition(position)
                                .toString()
                    }
                }
                activateBtn.setOnClickListener {
                    if (!section.isNullOrEmpty()) {
                        clickListener.onClick(FiltersData(section, sort, country, city))
                    }
                    bottomSheetDialog.cancel()
                }
                clearText.setOnClickListener {
                    sectionsAutoCompelete.setText(sectionname)
                    section = null
                    sortAutoCompelete.setText(sortname)
                    sort = null
                    countriesAutoCompelete.setText(countryName)
                    country = null
                    citiesAutoComplete.setText(cityName)
                    city = null
                    bottomSheetDialog.cancel()
                }
            }
            bottomSheetDialog.setContentView(binding.root)
            bottomSheetDialog.show()
        }
    }
}

