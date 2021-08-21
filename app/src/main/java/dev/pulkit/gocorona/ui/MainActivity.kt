package dev.pulkit.gocorona.ui

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dev.pulkit.gocorona.R
import dev.pulkit.gocorona.models.ctscan.Comment
import dev.pulkit.gocorona.ui.fragments.HomeFragment
import dev.pulkit.gocorona.ui.fragments.XRayScanFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_x_ray_scan.*
import android.content.res.ColorStateList




class MainActivity : AppCompatActivity() {

    private lateinit var navController:NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = fragMainActivity.findNavController()
        bnb_main.background = null
        bnb_main.itemIconTintList = ColorStateList(
            arrayOf(
                intArrayOf(-android.R.attr.state_checked),
                intArrayOf(android.R.attr.state_checked)
            ), intArrayOf(
                Color.parseColor("#000000"),
                Color.parseColor("#ffffff")
            )
        )
        bnb_main.setupWithNavController(navController)
        val listOfFragsWithNavBar = listOf(R.id.homeFragment,R.id.XRayReviewFragment,R.id.XRayScanFragment,R.id.aboutFragment)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id in listOfFragsWithNavBar){
                cl_bottom_bar_main.visibility = View.VISIBLE
                if(destination.id == R.id.XRayScanFragment){
                    fabBottomBar.setImageResource(R.drawable.xray_white)
                    fabBottomBar.setColorFilter(Color.WHITE)
                }else{
                    fabBottomBar.setImageResource(R.drawable.x_ray_black)
                }
            }else{
                cl_bottom_bar_main.visibility = View.GONE
            }
        }
    }
    fun navigateToXrayScanFragment(view: View){
        bnb_main.uncheckAllItems()
        val navHostFragment = supportFragmentManager.primaryNavigationFragment
        val fragment = navHostFragment?.childFragmentManager?.fragments?.get(0)
        if (fragment is HomeFragment){
            navController.navigate(R.id.action_homeFragment_to_XRayScanFragment)
        }else{
            navController.navigate(R.id.action_aboutFragment_to_XRayScanFragment)
        }
    }

    fun navigateToHistoryFragment(view:View) {
        navController.navigate(R.id.action_XRayScanFragment_to_historyMyXRaysFragment)
    }
    fun navigateToMaskFragment(view:View){
        navController.navigate(R.id.action_homeFragment_to_maskFragment)
    }
    fun navigateToGuidelinesFragment(view:View){
        navController.navigate(R.id.action_homeFragment_to_govtGuidelinesPDFFragment)
    }
    fun navigateToIsolationHelper(view:View){
        navController.navigate(R.id.action_homeFragment_to_isolationHelpersFragment)
    }
    fun navigateToComments(comments:ArrayList<Comment>){
        val bundle = Bundle()
        Log.d("pulkit","main Activity:" + comments.size)
        bundle.putSerializable("commentsList",comments)
        navController.navigate(R.id.action_historyMyXRaysFragment_to_myCTScanAllCommentsFragment,bundle)
    }
    fun navigateToCoronaCount(view: View){
        navController.navigate(R.id.action_homeFragment_to_covidTrackFragment)
    }
    fun navigateToVaccineCount(view:View){
        navController.navigate(R.id.action_homeFragment_to_vaccinationFragment)
    }
    fun donate(view: View){
        Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("https://www.pmcares.gov.in/en/")
            startActivity(this)
        }
    }
    fun registerVaccine(view:View){
        Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("https://selfregistration.cowin.gov.in/selfregistration")
            startActivity(this)
        }
    }
    fun BottomNavigationView.uncheckAllItems() {
        menu.setGroupCheckable(0, true, false)
        for (i in 0 until menu.size()) {
            menu.getItem(i).isChecked = false
        }
        menu.setGroupCheckable(0, true, true)
    }
}