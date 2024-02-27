package com.example.darestory.ui.main

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

        repeatOnStarted {
            launch {
                viewModel.eventFlow.collect {
                    sortEvent(it as MainEvent)
                }
            }
        }
    }

    private fun initNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_view_main_host) as NavHostFragment
        navController = navHostFragment.navController
        bindBottomNavigation()
    }

    private fun bindBottomNavigation(){
        binding.apply {
            bottomNavigationView.setupWithNavController(navController)
            bottomNavigationView.setOnItemSelectedListener {
                viewModel.onBottomNavigationItemSelected(it.itemId)
                true
            }
        }
    }

    private fun sortEvent(event: MainEvent){
        when(event){
            MainEvent.NavigateDiscussion -> navController.navigate(R.id.discussionFragment)
            MainEvent.NavigateHome -> navController.navigate(R.id.homeFragment)
            MainEvent.NavigateMy -> navController.navigate(R.id.myFragment)
        }
    }

    override fun onBackPressed() {
        // 현재 Fragment가 homeFragment인 경우에만 뒤로가기 버튼을 막음
        if (navController.currentDestination?.id == R.id.homeFragment ||
            navController.currentDestination?.id == R.id.discussionFragment ||
            navController.currentDestination?.id == R.id.myFragment) {
            finish()
            return
        }
        super.onBackPressed()
    }
}