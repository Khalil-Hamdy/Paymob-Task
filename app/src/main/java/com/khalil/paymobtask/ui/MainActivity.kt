package com.khalil.paymobtask.ui

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.khalil.paymobtask.R
import com.khalil.paymobtask.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _bindig: ActivityMainBinding? = null
    private val binding get() = _bindig!!
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _bindig = ActivityMainBinding.inflate(layoutInflater)

        val naveHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
                as NavHostFragment
        navController = naveHostFragment.navController

        setContentView(binding.root)
    }
}