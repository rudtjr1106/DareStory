package com.example.darestory.ui.main

import android.annotation.SuppressLint
import android.view.View
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.darestory.PageState
import com.example.darestory.R
import com.example.darestory.base.BaseActivity
import com.example.darestory.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity :
    BaseActivity<ActivityMainBinding, PageState.Default, MainViewModel>(ActivityMainBinding::inflate) {

    override val viewModel: MainViewModel by viewModels()
    private lateinit var navController: NavController

    override fun initView() {

        binding.apply {
            vm = viewModel
            initNavigation()
        }
    }

    override fun initState() {
        super.initState()


    }

    private fun initNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_view_main_host) as NavHostFragment
        navController = navHostFragment.navController
    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        navController.popBackStack()
    }
}