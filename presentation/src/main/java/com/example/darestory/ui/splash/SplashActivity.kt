package com.example.darestory.ui.splash

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.darestory.PageState
import com.example.darestory.R
import com.example.darestory.base.BaseActivity
import com.example.darestory.databinding.ActivitySplashBinding
import com.example.darestory.ui.common.CommonDialog
import com.example.darestory.ui.main.MainActivity
import com.example.darestory.ui.sign.SignActivity
import com.example.darestory.ui.sign.login.LoginEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding, PageState.Default, SplashViewModel>(
    ActivitySplashBinding::inflate) {

    @Inject
    lateinit var commonDialog: CommonDialog

    companion object {
        private const val PERMISSION_REQUEST_CODE = 5000
    }

    override val viewModel: SplashViewModel by viewModels()
    override fun initView() {

        binding.apply {
            vm = viewModel
            permissionCheck()
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
            is SplashEvent.ErrorServerEvent -> showServerErrorDialog(event.content)
            SplashEvent.ErrorVersionEvent -> TODO()
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

    private fun showServerErrorDialog(errorContent : String){
        commonDialog
            .setTitle(R.string.dialog_server_error_title)
            .setDescription(errorContent)
            .setPositiveButton(R.string.word_confirm){
                commonDialog.dismiss()
            }
    }

    private fun showVersionErrorDialog(){

    }
}