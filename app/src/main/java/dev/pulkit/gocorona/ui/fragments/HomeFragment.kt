package dev.pulkit.gocorona.ui.fragments

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.fragment.app.Fragment
import dev.pulkit.gocorona.R

class HomeFragment : Fragment(R.layout.fragment_home) {
    fun donate(view: View){
        Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("https://www.pmcares.gov.in/en/")
            startActivity(this)
        }
    }
}