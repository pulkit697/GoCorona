package dev.pulkit.gocorona.ui.fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import dev.pulkit.gocorona.R
import dev.pulkit.gocorona.adapters.MySliderViewAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.layout_sliders_homefrag.*
import kotlin.concurrent.thread

class HomeFragment : Fragment(R.layout.fragment_home) {

    lateinit var changeCardColorThread: Thread
    private var amIDoctor = false

    val bgColors = listOf<Int>(Color.parseColor("#DD2C00"),Color.parseColor("#FFD600"))
    val textColors = listOf<Int>(Color.parseColor("#6200EA"),Color.parseColor("#64DD17"))
    var k = 0
    var loopRunning = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        amIDoctor = requireActivity().getSharedPreferences("SharedPref", Context.MODE_PRIVATE).getBoolean(getString(R.string.areyoudoctor),false)
        val listOfImages = listOf(R.drawable.slider_0,R.drawable.slider_1,R.drawable.slider_2,R.drawable.slider_3)
        svHomeFragSlider.setSliderAdapter(MySliderViewAdapter(listOfImages))
        svHomeFragSlider.scrollTimeInSec = 3;
        svHomeFragSlider.isAutoCycle = true
        svHomeFragSlider.startAutoCycle()
        setUpColors()
    }

    override fun onResume() {
        super.onResume()
        loopRunning = true
        fluctuateColorsInCard()
        changeCardColorThread.start()
    }

    override fun onPause() {
        super.onPause()
        loopRunning = false
        try{
            changeCardColorThread.interrupt()
        }catch (e:Exception){
            /* Do Nothing */
        }
    }

    private fun setUpColors(){
        if(amIDoctor){
            tvGet.text = "Review "
            tvCTScanCard.text = "CT Scans"
            tvTested.text = ""
        }else{
            tvGet.text = "Get "
            tvCTScanCard.text = "CT Scan"
            tvTested.text = " Tested"
        }
    }

    private fun fluctuateColorsInCard(){
        changeCardColorThread = thread(start = false){
            try {
                while (loopRunning && !Thread.currentThread().isInterrupted) {
                    layGetTestedHome.setCardBackgroundColor(bgColors[k++ % bgColors.size])
                    tvCTScanCard.setTextColor(textColors[k % bgColors.size])
                    Thread.sleep(300)
                }
            }catch (e:Exception){
                Thread.currentThread().interrupt()
            }
        }
    }
}