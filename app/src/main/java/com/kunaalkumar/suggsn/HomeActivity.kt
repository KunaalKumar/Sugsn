package com.kunaalkumar.suggsn

import android.os.Bundle
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.kunaalkumar.suggsn.repositories.TmdbRepository
import kotlinx.android.synthetic.main.activity_home.*
import kotlin.math.roundToInt

class HomeActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        navController = findNavController(R.id.nav_host_fragment)

        // Get screen size and convert to pixels from dpi for poster images
        val displayMetric = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetric)
        TmdbRepository.WIDTH = (displayMetric.widthPixels * 0.5).roundToInt()
        TmdbRepository.HEIGHT = (TmdbRepository.WIDTH * 1.5).roundToInt()

        bottom_nav_bar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home ->
                    navController.navigate(R.id.home_dest)
                R.id.nav_movies ->
                    navController.navigate(R.id.movies_dest)
                R.id.nav_tv ->
                    navController.navigate(R.id.tvs_dest)
                R.id.nav_people ->
                    navController.navigate(R.id.people_dest)
            }
            return@setOnNavigationItemSelectedListener true
        }
    }
}