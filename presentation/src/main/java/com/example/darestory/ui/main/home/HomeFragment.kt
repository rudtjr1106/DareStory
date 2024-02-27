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
import com.example.darestory.util.DareToast
import com.example.domain.model.enums.HomeViewType
import com.example.domain.model.enums.ToastType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, PageState.Default, HomeViewModel>(
    FragmentHomeBinding::inflate
) {

    override val viewModel: HomeViewModel by viewModels()

    private val homeAdapter : HomeAdapter by lazy {
        HomeAdapter(object : HomeAdapter.HomeDelegate {

        })
    }

    override fun initView() {
        binding.apply {
            vm = viewModel
            recyclerHome.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = homeAdapter
            }

            homeAdapter.submitList(listOf(HomeViewType.TODAY_PROSE, HomeViewType.ALL_PROSE))
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
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