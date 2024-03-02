package com.example.darestory.ui.main.home.detail

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.darestory.base.BaseFragment
import com.example.darestory.databinding.FragmentDetailBinding
import com.example.darestory.ui.common.CommonBottomSheet
import com.example.darestory.ui.main.home.adapter.HomeAdapter
import com.example.darestory.ui.main.home.detail.adapter.DetailPageAdapter
import com.example.darestory.ui.sign.signUpProfile.SignUpProfileFragmentArgs
import com.example.darestory.util.DareLog
import com.example.domain.model.enums.BottomSheetType
import com.example.domain.model.enums.DetailType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding, DetailPageState, DetailViewModel>(
    FragmentDetailBinding::inflate
) {

    override val viewModel: DetailViewModel by viewModels()
    private val detailFragmentArgs : DetailFragmentArgs by navArgs()

    private val detailPagerAdapter : DetailPageAdapter by lazy {
        DetailPageAdapter(object : DetailPageAdapter.DetailPageDelegate {
            override fun onClickLike(id: Int, type: DetailType, isLiked : Boolean) {
                viewModel.onClickLikeBtn(id, type, isLiked)
            }

            override fun onClickBack() {
                viewModel.onClickBackButton()
            }

            override fun onClickMenu(type : DetailType) {
                viewModel.onClickContentMenu(type)
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
        }
    }

    private fun showBottomSheet(type : BottomSheetType){
        CommonBottomSheet.newInstance(type) {
            viewModel.onClickImageMenuItemType(it)
        }.show(parentFragmentManager, "")
    }

    override fun onStart() {
        super.onStart()
    }
}