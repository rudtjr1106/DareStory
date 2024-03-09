package com.example.darestory.ui.main.my

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.darestory.R
import com.example.darestory.base.BaseFragment
import com.example.darestory.databinding.FragmentMyBinding
import com.example.darestory.ui.common.CommonDialog
import com.example.darestory.ui.sign.SignActivity
import com.example.domain.model.enums.DetailType
import com.example.domain.model.enums.ReadOrOwnType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MyFragment : BaseFragment<FragmentMyBinding, MyPageState, MyViewModel>(
    FragmentMyBinding::inflate
) {
    companion object{
        const val TEXT_MAILTO = "mailto"
    }

    @Inject
    lateinit var commonDialog: CommonDialog

    override val viewModel: MyViewModel by viewModels()

    override fun initView() {
        binding.apply {
            vm = viewModel
            viewModel.getMyInfo()
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.eventFlow.collect {
                    sortEvent(it as MyEvent)
                }
            }
        }
    }

    private fun sortEvent(event: MyEvent){
        when(event){
            is MyEvent.GoToMyProseAndDiscussionEvent -> goToMyProseAndDiscussion(event.type)
            is MyEvent.GoToMyReadOrOwnBookEvent -> goToMyReadOrOwnBook(event.type)
            is MyEvent.ShowLogoutDialogEvent -> showLogoutDialog()
            is MyEvent.ShowUnRegisterDialogEvent -> showUnregisterDialog()
            is MyEvent.GoToLoginEvent -> goToLoginPage()
            is MyEvent.GoToNoticeEvent -> goToNoticePage()
            is MyEvent.GoToSendEmailEvent -> sendToEmail()
            MyEvent.GoToDiscussionEvent -> goToDiscussionPage()
            MyEvent.GoToHomeEvent -> goToHomePage()
        }
    }

    private fun goToMyProseAndDiscussion(type : DetailType){
        val action = MyFragmentDirections.actionMyToMyProseAndDiscussion(type)
        findNavController().navigate(action)
    }

    private fun goToMyReadOrOwnBook(type : ReadOrOwnType){
        val action = MyFragmentDirections.actionMyToMyReadOrOwnBook(type)
        findNavController().navigate(action)
    }

    private fun showLogoutDialog(){
        commonDialog
            .setTitle(R.string.word_logout)
            .setDescription(R.string.my_logout_dialog)
            .setNegativeButton(R.string.word_cancel){
                commonDialog.dismiss()
            }
            .setPositiveButton(R.string.word_logout){
                viewModel.logout()
                commonDialog.dismiss()
            }
            .show()
    }

    private fun showUnregisterDialog(){
        commonDialog
            .setTitle(R.string.word_unregister)
            .setDescription(R.string.my_unregister_dialog)
            .setNegativeButton(R.string.word_cancel){
                commonDialog.dismiss()
            }
            .setPositiveButton(R.string.word_unregister){
                viewModel.unregister()
                commonDialog.dismiss()
            }
            .show()
    }

    private fun goToLoginPage(){
        val intent = Intent(requireContext(), SignActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun goToNoticePage(){
        val action = MyFragmentDirections.actionMyToNotice()
        findNavController().navigate(action)
    }

    private fun goToHomePage(){
        val action = MyFragmentDirections.actionMyToHome()
        findNavController().navigate(action)
    }
    private fun goToDiscussionPage(){
        val action = MyFragmentDirections.actionMyToDiscussion()
        findNavController().navigate(action)
    }

    private fun sendToEmail(){
        val intent = Intent(Intent.ACTION_SENDTO, Uri.fromParts(
            TEXT_MAILTO, context?.getString(R.string.dare_story_email) ?: "", null)
        )
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
    }
}