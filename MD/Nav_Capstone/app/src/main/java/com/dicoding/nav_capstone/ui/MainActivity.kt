package com.dicoding.nav_capstone.ui

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.dicoding.nav_capstone.R
import com.dicoding.nav_capstone.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.navigation_home,
//                R.id.navigation_list,
//                R.id.navigation_scan,
//                R.id.navigation_favorite,
//                R.id.navigation_profile
//            )
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


//            supportActionBar?.hide()

        // Set the itemIconTintList property
//            binding.navView.itemIconTintList = ColorStateList(
//                arrayOf(
//                    intArrayOf(android.R.attr.state_checked, resources.getColor(R.color.grey1)),
//                    intArrayOf(
//                        -android.R.attr.state_checked,
//                        resources.getColor(R.color.primaryColor)
//                    )
//                ),
//                intArrayOf(
//                    resources.getColor(R.color.grey1),
//                    resources.getColor(R.color.primaryColor)
//                )
//            )
    }
}


