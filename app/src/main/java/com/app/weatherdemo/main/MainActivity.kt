package com.app.weatherdemo.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.app.weatherdemo.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import com.app.weatherdemo.R
import com.app.weatherdemo.apputils.AppSharedPrefrences
import com.app.weatherdemo.apputils.setFullScreenForNotch
import com.app.weatherdemo.apputils.setFullScreenWithBtmNav
import com.app.weatherdemo.ui.home.HomeViewModel
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var mySharedPrefrences: AppSharedPrefrences

    val navController:NavController by lazy {
        findNavController(R.id.actxfragment)
    }
    lateinit var binding: ActivityMainBinding
    val mainViewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }


}