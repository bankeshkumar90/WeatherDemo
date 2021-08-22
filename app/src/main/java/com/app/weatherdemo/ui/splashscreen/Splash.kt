package com.app.weatherdemo.ui.splashscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.app.weatherdemo.R
import com.app.weatherdemo.databinding.FragmentSplashBinding
import com.app.weatherdemo.apputils.CoroutinesHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Splash : Fragment() {
    private val splashViewModel:SplashViewModel by lazy {
        ViewModelProvider(this).get(SplashViewModel::class.java)
    }
    lateinit var binding: FragmentSplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        gotoHome()
        return binding.root
    }

    private fun gotoHome(){
        CoroutinesHelper.delayMain(2000){
            findNavController().navigate(R.id.action_splash_to_homeFragment)
        }
    }


}