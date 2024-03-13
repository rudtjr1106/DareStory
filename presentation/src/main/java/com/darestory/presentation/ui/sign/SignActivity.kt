package com.darestory.presentation.ui.sign

import android.annotation.SuppressLint
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.darestory.presentation.BuildConfig
import com.darestory.presentation.PageState
import com.darestory.presentation.R
import com.darestory.presentation.base.BaseActivity
import com.darestory.presentation.databinding.ActivitySignBinding
import com.darestory.presentation.util.DareLog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignActivity : BaseActivity<ActivitySignBinding, PageState.Default, SignViewModel>(ActivitySignBinding::inflate) {

    override val viewModel: SignViewModel by viewModels()
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
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        navController = navHostFragment.navController
    }
}