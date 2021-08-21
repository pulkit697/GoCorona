package dev.pulkit.gocorona.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import dev.pulkit.gocorona.R
import dev.pulkit.gocorona.adapters.MySliderViewAdapter
import kotlinx.android.synthetic.main.layout_sliders_homefrag.*

class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listOfImages = listOf(R.drawable.slider_0,R.drawable.slider_1,R.drawable.slider_2,R.drawable.slider_3)
        svHomeFragSlider.setSliderAdapter(MySliderViewAdapter(listOfImages))
        svHomeFragSlider.scrollTimeInSec = 3;
        svHomeFragSlider.isAutoCycle = true
        svHomeFragSlider.startAutoCycle()
    }
}