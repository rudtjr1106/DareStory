package com.example.darestory.ui.main.discussion.replyComment

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.darestory.R
import com.example.darestory.base.BaseFragment
import com.example.darestory.databinding.FragmentDiscussionReplyCommentBinding
import com.example.darestory.ui.common.CommonBottomSheet
import com.example.darestory.ui.common.CommonDialog
import com.example.darestory.ui.main.discussion.replyComment.adapter.DiscussionCommentAdapter
import com.example.domain.model.enums.BottomSheetType
import com.example.domain.model.enums.CommentType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DiscussionReplyCommentFragment : BaseFragment<FragmentDiscussionReplyCommentBinding, DiscussionReplyCommentPageState, DiscussionReplyCommentViewModel>(
    FragmentDiscussionReplyCommentBinding::inflate
) {

    @Inject
    lateinit var commonDialog: CommonDialog

    override val viewModel: DiscussionReplyCommentViewModel by viewModels()

    private val disReplyCommentFragmentArgs : DiscussionReplyCommentFragmentArgs by navArgs()

    private val commentAdapter = DiscussionCommentAdapter(object : DiscussionCommentAdapter.DiscussionCommentDelegate{
        override fun onClickCommentMenu(commentId: Int, writer: String, type: CommentType) {
            viewModel.onClickCommentMenu(commentId, writer, type)
        }
    })

    override fun initView() {
        binding.apply {
            vm = viewModel

            recyclerComment.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = commentAdapter
                itemAnimator = null
            }

            viewModel.loadPage(disReplyCommentFragmentArgs.discussionId, disReplyCommentFragmentArgs.commentId)
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.commentList.collect{
                    commentAdapter.submitList(it)
                }
            }
            launch {
                viewModel.eventFlow.collect {
                    sortEvent(it as DiscussionReplyCommentEvent)
                }
            }
        }
    }

    private fun sortEvent(event: DiscussionReplyCommentEvent){
        when(event){
            is DiscussionReplyCommentEvent.GoReportEvent -> goReportPage(event.who)
            DiscussionReplyCommentEvent.GoToBack -> findNavController().popBackStack()
            is DiscussionReplyCommentEvent.ShowBottomSheetEvent -> showBottomSheet(event.type)
            DiscussionReplyCommentEvent.ShowCommentDeleteDialogEvent -> showCommentDeleteDialog()
            DiscussionReplyCommentEvent.DeleteCommentErrorEvent -> showDeleteDiscussionCommentErrorDialog()
        }
    }

    private fun goReportPage(who : String){
        val action = DiscussionReplyCommentFragmentDirections.actionDiscussionReplyCommentToReport(who)
        findNavController().navigate(action)
    }

    private fun showDeleteDiscussionCommentErrorDialog(){
        commonDialog
            .setTitle(R.string.dialog_delete_comment_error_title)
            .setDescription(R.string.dialog_delete_comment_error_content)
            .setPositiveButton(R.string.word_confirm){
                findNavController().popBackStack()
                commonDialog.dismiss()
            }
            .showOnlyPositive()
            .show()
    }

    private fun showBottomSheet(type : BottomSheetType){
        CommonBottomSheet.newInstance(type) {
            viewModel.onClickImageMenuItemType(it)
        }.show(parentFragmentManager, "")
    }

    private fun showCommentDeleteDialog(){
        commonDialog
            .setTitle(R.string.dialog_delete_title)
            .setDescription(R.string.dialog_delete_content)
            .setPositiveButton(R.string.word_delete){
                viewModel.deleteComment(disReplyCommentFragmentArgs.discussionId, disReplyCommentFragmentArgs.commentId)
                commonDialog.dismiss()
            }
            .setNegativeButton(R.string.word_cancel){
                commonDialog.dismiss()
            }
            .show()
    }


    override fun onStart() {
        super.onStart()
    }
}