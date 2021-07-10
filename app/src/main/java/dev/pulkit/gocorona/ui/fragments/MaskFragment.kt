package dev.pulkit.gocorona.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import dev.pulkit.gocorona.R
import kotlinx.android.synthetic.main.fragment_mask.*

class MaskFragment : Fragment(R.layout.fragment_mask) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btFragMaskBuyTripleMask.setOnClickListener { buyMask(false) }
        btFragMaskBuyN95Mask.setOnClickListener { buyMask(true) }
    }
    private fun buyMask(b:Boolean){
        // b == true => buy n95 mask
        // else => buy triple mask
        var url = "https://www.google.com/search?q=triple+layer+face+mask&tbm=shop"
        if(b){
           url = "https://www.amazon.in/b?ie=UTF8&node=21460753031"
        }
        val i = Intent(Intent.ACTION_VIEW)
        i.apply {
            data = Uri.parse(url)
        }
        startActivity(i)
    }
}