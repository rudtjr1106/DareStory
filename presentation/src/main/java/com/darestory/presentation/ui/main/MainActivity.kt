package com.darestory.presentation.ui.main

import android.Manifest
import android.Manifest.permission.SCHEDULE_EXACT_ALARM
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.darestory.presentation.PageState
import com.darestory.presentation.R
import com.darestory.presentation.base.BaseActivity
import com.darestory.presentation.databinding.ActivityMainBinding
import com.darestory.presentation.ui.splash.SplashActivity
import com.darestory.presentation.util.DareLog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity :
    BaseActivity<ActivityMainBinding, PageState.Default, MainViewModel>(ActivityMainBinding::inflate) {

    companion object {
        private const val PERMISSION_REQUEST_CODE = 5000
    }

    override val viewModel: MainViewModel by viewModels()
    private lateinit var navController: NavController

    override fun initView() {

        binding.apply {
            vm = viewModel
            permissionCheck()
            initNavigation()
        }
    }

    override fun initState() {
        super.initState()


    }

    private fun permissionCheck() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permissionCheck = ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.POST_NOTIFICATIONS
            )
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                    PERMISSION_REQUEST_CODE
                )
            }
        }
    }

    private fun initNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_view_main_host) as NavHostFragment
        navController = navHostFragment.navController

    }
}