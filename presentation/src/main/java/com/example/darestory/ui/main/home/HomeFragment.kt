package com.example.darestory.ui.main.home

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.darestory.PageState
import com.example.darestory.R
import com.example.darestory.base.BaseFragment
import com.example.darestory.databinding.FragmentHomeBinding
import com.example.darestory.databinding.FragmentLoginBinding
import com.example.darestory.ui.common.InputDialog
import com.example.darestory.ui.main.home.adapter.HomeAdapter
import com.example.darestory.ui.sign.login.LoginEvent
import com.example.darestory.ui.sign.login.LoginFragmentDirections
import com.example.darestory.ui.sign.login.LoginPageState
import com.example.darestory.ui.sign.login.LoginViewModel
import com.example.darestory.util.DareLog
import com.example.darestory.util.DareToast
import com.example.domain.model.enums.HomeViewType
import com.example.domain.model.enums.ToastType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomePageState, HomeViewModel>(
    FragmentHomeBinding::inflate
) {

    override val viewModel: HomeViewModel by viewModels()

    private val homeAdapter : HomeAdapter by lazy {
        HomeAdapter(object : HomeAdapter.HomeDelegate {
            override fun onClickTodayProse() {
                DareLog.D("오늘의 산문")
            }

            override fun onClickSortPopular() {
                DareLog.D("정렬 인기순")
            }

            override fun onClickSortRecent() {
                DareLog.D("정렬 최신순")
            }

            override fun onClickSortAge() {
                DareLog.D("정렬 나이순")
            }

            override fun onClickAllProse() {
                DareLog.D("일반 산문")
            }
        })
    }

    override fun initView() {
        binding.apply {
            vm = viewModel
            recyclerHome.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = homeAdapter
            }

            viewModel.getAllProse()
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.proseList.collect{
                    homeAdapter.submitList(it)
                }
            }
            launch {
                viewModel.eventFlow.collect {

                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
    }
}