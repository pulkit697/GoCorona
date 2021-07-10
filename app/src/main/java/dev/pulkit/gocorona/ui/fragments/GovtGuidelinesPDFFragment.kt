package dev.pulkit.gocorona.ui.fragments

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import dev.pulkit.gocorona.R
import kotlinx.android.synthetic.main.fragment_govt_guidelines_p_d_f.*


class GovtGuidelinesPDFFragment : Fragment(R.layout.fragment_govt_guidelines_p_d_f) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pdfViewFragGovtGuidelines.webViewClient = WebViewClient()
        pdfViewFragGovtGuidelines.settings.setSupportZoom(true)
        pdfViewFragGovtGuidelines.settings.javaScriptEnabled = true
        val url = "https://www.mohfw.gov.in/pdf/GuidelinesonrationaluseofPersonalProtectiveEquipment.pdf"
        pdfViewFragGovtGuidelines.loadUrl("https://docs.google.com/gview?embedded=true&url=$url")
    }
}