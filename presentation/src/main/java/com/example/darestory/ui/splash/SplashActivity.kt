package com.example.darestory.ui.splash

import android.content.Intent
import androidx.activity.viewModels
import com.example.darestory.PageState
import com.example.darestory.base.BaseActivity
import com.example.darestory.databinding.ActivitySplashBinding
import com.example.darestory.ui.main.MainActivity
import com.example.darestory.ui.sign.SignActivity
import com.example.darestory.ui.sign.login.LoginEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding, PageState.Default, SplashViewModel>(
    ActivitySplashBinding::inflate) {

    override val viewModel: SplashViewModel by viewModels()
    override fun initView() {

        binding.apply {
            vm = viewModel
            viewModel.checkLogin()
        }
    }

    override fun initState() {
        super.initState()

        repeatOnStarted {
            launch {
                viewModel.eventFlow.collect {
                    sortEvent(it as SplashEvent)
                }
            }
        }
    }

    private fun sortEvent(event: SplashEvent){
        when(event){
            SplashEvent.GoToLoginEvent -> goToLogin()
            SplashEvent.GoToMainEvent -> goToMain()
        }
    }

    private fun goToMain(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun goToLogin(){
        val intent = Intent(this, SignActivity::class.java)
        startActivity(intent)
        finish()
    }

}