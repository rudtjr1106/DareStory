package com.example.darestory.ui.main.search.recent

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.darestory.base.BaseFragment
import com.example.darestory.databinding.FragmentRecentSearchBinding
import com.example.darestory.ui.main.home.adapter.HomeAdapter
import com.example.darestory.ui.main.search.recent.adapter.RecentSearchAdapter
import com.example.darestory.util.DareLog
import com.example.domain.model.enums.SortType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecentSearchFragment : BaseFragment<FragmentRecentSearchBinding, RecentSearchPageState, RecentSearchViewModel>(
    FragmentRecentSearchBinding::inflate
) {

    override val viewModel: RecentSearchViewModel by viewModels()

    private val recentSearchAdapter : RecentSearchAdapter by lazy {
        RecentSearchAdapter(object : RecentSearchAdapter.RecentSearchDelegate {
            override fun onClickItem(recent: String) {
                DareLog.D(recent)
            }
        })
    }

    override fun initView() {
        binding.apply {
            vm = viewModel
            recyclerRecentSearch.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = recentSearchAdapter
            }

            viewModel.test1()
            viewModel.test2()
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