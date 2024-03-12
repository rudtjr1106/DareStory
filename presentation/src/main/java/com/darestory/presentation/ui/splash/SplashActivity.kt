package com.darestory.presentation.ui.splash

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.darestory.presentation.PageState
import com.darestory.presentation.R
import com.darestory.presentation.base.BaseActivity
import com.darestory.presentation.databinding.ActivitySplashBinding
import com.darestory.presentation.ui.common.CommonDialog
import com.darestory.presentation.ui.main.MainActivity
import com.darestory.presentation.ui.sign.SignActivity
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
            viewModel.checkAppInfo()
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
            SplashEvent.ErrorVersionEvent -> showVersionErrorDialog()
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
                finish()
            }
            .show()
    }

    private fun showVersionErrorDialog(){
        commonDialog
            .setTitle(R.string.dialog_version_error_title)
            .setDescription(R.string.dialog_version_error_content)
            .setPositiveButton(R.string.word_confirm){
                commonDialog.dismiss()
                finish()
            }
            .show()
    }
}