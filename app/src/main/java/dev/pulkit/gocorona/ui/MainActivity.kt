package dev.pulkit.gocorona.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import dev.pulkit.gocorona.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_x_ray_scan.*

class MainActivity : AppCompatActivity() {

    private lateinit var navController:NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = fragMainActivity.findNavController()
        bnb_main.setupWithNavController(navController)
        val listOfFragsWithNavBar = listOf(R.id.homeFragment,R.id.XRayReviewFragment,R.id.XRayScanFragment,R.id.aboutFragment)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id in listOfFragsWithNavBar){
                bnb_main.visibility = View.VISIBLE
            }else{
                bnb_main.visibility = View.GONE
            }
        }
    }
    fun navigateToHistoryFragment(view:View) {
        navController.navigate(R.id.action_XRayScanFragment_to_historyMyXRaysFragment)
    }
}