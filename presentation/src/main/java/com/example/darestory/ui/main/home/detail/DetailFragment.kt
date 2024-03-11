package com.example.darestory.ui.main.home.detail

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.darestory.R
import com.example.darestory.base.BaseFragment
import com.example.darestory.databinding.FragmentDetailBinding
import com.example.darestory.ui.common.CommonBottomSheet
import com.example.darestory.ui.common.CommonDialog
import com.example.darestory.ui.main.home.detail.adapter.DetailPageAdapter
import com.example.domain.model.enums.BottomSheetType
import com.example.domain.model.enums.DetailType
import com.example.domain.model.enums.ReadOrOwnType
import com.example.domain.model.enums.WriteType
import com.example.domain.model.vo.DisCommentVo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding, DetailPageState, DetailViewModel>(
    FragmentDetailBinding::inflate
) {

    @Inject
    lateinit var commonDialog: CommonDialog

    override val viewModel: DetailViewModel by viewModels()
    private val detailFragmentArgs : DetailFragmentArgs by navArgs()

    private val detailPagerAdapter : DetailPageAdapter by lazy {
        DetailPageAdapter(object : DetailPageAdapter.DetailPageDelegate {
            override fun onClickLike(id: Int, isLiked : Boolean) {
                viewModel.onClickLikeBtn(id, detailFragmentArgs.detailType, isLiked)
            }

            override fun onClickBack() {
                viewModel.onClickBackButton()
            }

            override fun onClickMenu(author : String) {
                viewModel.onClickContentMenu(detailFragmentArgs.detailType, author)
            }

            override fun onClickCommentMenu(commentId : Int, writer : String) {
                viewModel.onClickCommentMenu(commentId, detailFragmentArgs.detailType, writer)
            }

            override fun onClickReplyComment(commentVo: DisCommentVo) {
                goToDiscussionReplyComment(commentVo)
            }
        })
    }


    override fun initView() {
        binding.apply {
            vm = viewModel

            recyclerDetail.apply {
                itemAnimator = null
                layoutManager = LinearLayoutManager(context)
                adapter = detailPagerAdapter
            }

            viewModel.getDetail(detailFragmentArgs.detailId, detailFragmentArgs.detailType)
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.detailPageList.collect{
                    detailPagerAdapter.submitList(it)
                }
            }
            launch {
                viewModel.eventFlow.collect {
                    sortEvent(it as DetailEvent)
                }
            }
        }
    }

    private fun sortEvent(event: DetailEvent){
        when(event){
            is DetailEvent.GoToBack -> findNavController().popBackStack()
            is DetailEvent.ShowBottomSheetEvent -> showBottomSheet(event.type)
            is DetailEvent.GoEditEvent -> goEditPage()
            is DetailEvent.ShowDeleteDialogEvent -> showDeleteDialog()
            is DetailEvent.ShowCommentDeleteDialogEvent -> showCommentDeleteDialog()
            is DetailEvent.GoReportEvent -> goReportPage(event.who)
            DetailEvent.GoToMyOwnBookSelectEvent -> goToMyReadOrOwnBook()
            DetailEvent.DeleteProseErrorEvent -> showDeleteProseErrorDialog()
        }
    }

    private fun showBottomSheet(type : BottomSheetType){
        CommonBottomSheet.newInstance(type) {
            viewModel.onClickImageMenuItemType(it)
        }.show(parentFragmentManager, "")
    }

    private fun goEditPage(){
        val action = when(detailFragmentArgs.detailType){
            DetailType.PROSE -> {
                DetailFragmentDirections.actionDetailToProseWrite(proseId = detailFragmentArgs.detailId, proseWriteType = WriteType.EDIT)
            }
            DetailType.DISCUSSION -> {
                DetailFragmentDirections.actionDetailToDiscussionWrite(discussionId = detailFragmentArgs.detailId, discussionWriteType = WriteType.EDIT)
            }
            DetailType.BOOK -> { DetailFragmentDirections.actionDetailToProseWrite(proseId = detailFragmentArgs.detailId, proseWriteType = WriteType.EDIT) }
        }
        findNavController().navigate(action)
    }

    private fun showDeleteDialog(){
        commonDialog
            .setTitle(R.string.dialog_delete_title)
            .setDescription(R.string.dialog_delete_content)
            .setPositiveButton(R.string.word_delete){
                viewModel.deleteThis(detailFragmentArgs.detailId)
                commonDialog.dismiss()
            }
            .setNegativeButton(R.string.word_cancel){
                commonDialog.dismiss()
            }
            .show()
    }

    private fun showCommentDeleteDialog(){
        commonDialog
            .setTitle(R.string.dialog_delete_title)
            .setDescription(R.string.dialog_delete_content)
            .setPositiveButton(R.string.word_delete){
                viewModel.deleteComment(detailFragmentArgs.detailId)
                commonDialog.dismiss()
            }
            .setNegativeButton(R.string.word_cancel){
                commonDialog.dismiss()
            }
            .show()
    }

    private fun showDeleteProseErrorDialog(){
        commonDialog
            .setTitle(R.string.dialog_delete_prose_error_title)
            .setDescription(R.string.dialog_delete_prose_error_content)
            .setPositiveButton(R.string.word_confirm){
                findNavController().popBackStack()
                commonDialog.dismiss()
            }
            .showOnlyPositive()
            .show()
    }

    private fun goReportPage(who : String){
        val action = DetailFragmentDirections.actionDetailToReport(who)
        findNavController().navigate(action)
    }

    private fun goToDiscussionReplyComment(disCommentVo: DisCommentVo){
        val action = DetailFragmentDirections.actionDetailToDiscussionReplyComment(
            discussionId = detailFragmentArgs.detailId,
            commentId = disCommentVo.commentId
        )
        findNavController().navigate(action)
    }

    private fun goToMyReadOrOwnBook(){
        val action = DetailFragmentDirections.actionDetailToMyReadOrOwnBook(
            type = ReadOrOwnType.SELECT_OWN_BOOK
        )
        findNavController().navigate(action)
    }

    override fun onResume() {
        super.onResume()
        viewModel.updateMyOwnBookProse()
    }

    override fun onStart() {
        super.onStart()
    }
}