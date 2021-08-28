package dev.pulkit.gocorona.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import dev.pulkit.gocorona.R
import kotlinx.android.synthetic.main.fragment_isolation_helpers.*

class IsolationHelpersFragment : Fragment(R.layout.fragment_isolation_helpers) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvOpenConsultDoctor.setOnClickListener { consultDoctor() }
        tvOpenYogaAndFitness.setOnClickListener { doYoga() }
        tvOpenMedicinalItems.setOnClickListener { buyMedicines() }
        tvOpenFruitsAndVegetables.setOnClickListener { buyFruitsVegetables() }
        tvOpenGrocery.setOnClickListener { buyGrocery() }
    }
    private fun consultDoctor(){
        val url:String = when{
            rbApollo.isChecked -> "https://www.apollo247.com/specialties"
            rbPracto.isChecked -> "https://www.practo.com/consult?utm_source=consumer-home&utm_medium=web&utm_campaign=core"
            rbDocsApp.isChecked -> "https://www.docsapp.in/"
            rbConsultDoctor.isChecked -> "https://www.google.com/search?q=consult+doctor+online"
            else -> ""
        }
        openUrl(url)
    }
    private fun doYoga(){
        val url:String = when{
            rbCultFit.isChecked -> "https://www.cult.fit/mind/therapy"
            rbGlo.isChecked -> "https://www.glo.com/"
            rbYogaTrackYoga.isChecked -> "https://trackyoga.app/"
            rbYogaAndFitnessOnline.isChecked -> "https://www.google.com/search?q=yoga+and+fitness+online"
            else -> ""
        }
        openUrl(url)
    }
    private fun buyMedicines(){
        val url:String = when{
            rbNetMeds.isChecked -> "https://www.netmeds.com/"
            rbPharmeasy.isChecked -> "https://pharmeasy.in/online-medicine-order"
            rb1Mg.isChecked -> "https://www.1mg.com/"
            rbMedicinesOnline.isChecked -> "https://www.google.com/search?q=buy+and+medicines+online"
            else -> ""
        }
        openUrl(url)
    }
    private fun buyFruitsVegetables(){
        val url:String = when{
            rbBigBasket.isChecked -> "https://www.bigbasket.com/cl/fruits-vegetables/"
            rbGrofers.isChecked -> "https://grofers.com/cn/vegetables-fruits/cid/1487"
            rbSpencers.isChecked -> "https://www.spencers.in/fruits-vegetables.html"
            rbFruitsAndVegetablesOnline.isChecked -> "https://www.google.com/search?q=fruits+and+vegetables+online"
            else -> ""
        }
        openUrl(url)
    }
    private fun buyGrocery(){
        val url:String = when{
            rbAmazonPantry.isChecked -> "https://www.amazon.in/pantry-online-grocery-shopping-store/b?ie=UTF8&node=9574332031"
            rbFlipkartGrocery.isChecked -> "https://www.flipkart.com/grocery-supermart-store?marketplace=GROCERY"
            rbJioMart.isChecked -> "https://www.jiomart.com/groceries"
            rbGroceryOnline.isChecked -> "https://www.google.com/search?q=buy+grocery+online"
            else -> ""
        }
        openUrl(url)
    }
    private fun openUrl(url:String){
        if (url == ""){
            Toast.makeText(requireContext(), "Please Choose one of the options", Toast.LENGTH_SHORT).show()
        }else {
            val i = Intent(Intent.ACTION_VIEW)
            i.apply {
                data = Uri.parse(url)
            }
            startActivity(i)
        }
    }
}