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
import com.example.domain.model.enums.WriteType
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
            is DetailEvent.ShowProseDeleteDialogEvent -> showProseDeleteDialog()
            is DetailEvent.ShowCommentDeleteDialogEvent -> showCommentDeleteDialog()
            is DetailEvent.GoReportEvent -> goReportPage(event.who)
        }
    }

    private fun showBottomSheet(type : BottomSheetType){
        CommonBottomSheet.newInstance(type) {
            viewModel.onClickImageMenuItemType(it)
        }.show(parentFragmentManager, "")
    }

    private fun goEditPage(){
        val action = when(detailFragmentArgs.detailType){
            DetailType.PROSE -> DetailFragmentDirections.actionDetailToProseWrite(proseId = detailFragmentArgs.detailId, proseWriteType = WriteType.EDIT)
            //TODO 토론장 페이지 edit은 따로 해야댐
            DetailType.DISCUSSION -> DetailFragmentDirections.actionDetailToProseWrite(proseId = detailFragmentArgs.detailId, proseWriteType = WriteType.EDIT)
        }
        findNavController().navigate(action)
    }

    private fun showProseDeleteDialog(){
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

    private fun goReportPage(who : String){
        val action = DetailFragmentDirections.actionDetailToReport(who)
        findNavController().navigate(action)
    }

    override fun onStart() {
        super.onStart()
    }
}