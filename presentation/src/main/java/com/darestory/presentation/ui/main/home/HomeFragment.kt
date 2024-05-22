package com.darestory.presentation.ui.main.home

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.darestory.presentation.base.BaseFragment
import com.darestory.presentation.databinding.FragmentHomeBinding
import com.darestory.presentation.ui.main.home.adapter.HomeAdapter
import com.darestory.domain.model.enums.DetailType
import com.darestory.domain.model.enums.WriteType
import com.darestory.domain.model.enums.SortType
import com.darestory.domain.model.vo.NotificationVo
import com.darestory.presentation.FcmNotification
import com.darestory.presentation.util.DareLog
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomePageState, HomeViewModel>(
    FragmentHomeBinding::inflate
) {
    override val viewModel: HomeViewModel by viewModels()

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    companion object {
        const val SHOW_SCROLL_UL_ICON_NUM = 20
    }

    private val homeAdapter: HomeAdapter by lazy {
        HomeAdapter(object : HomeAdapter.HomeDelegate {
            override fun onClickSearch() {
                FirebaseMessaging.getInstance().token.addOnSuccessListener { token ->
                    DareLog.D(token)
                    val fcm = FcmNotification()
                    fcm.sendMessage(
                        NotificationVo(
                            to = "fcm 키",
                            data = NotificationVo.Data(
                                title = "알람 테스트",
                                body = "알람테스트쓰쓰쓰쓰"
                            )
                        )
                    )
                }
            }

            override fun onClickProse(proseId: Int) {
                viewModel.goToDetailPage(proseId)
            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onClickSort(type: SortType) {
                viewModel.updateSortType(type)
                homeAdapter.sortType = type
            }

            override fun onClickWriteProse() {
                viewModel.onClickWriteProseBtn()
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun requestAlarmPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.SCHEDULE_EXACT_ALARM)
            != PackageManager.PERMISSION_GRANTED
        ) {
            DareLog.D("이게 뭘까 요총스")
            requestPermissionLauncher.launch(Manifest.permission.SCHEDULE_EXACT_ALARM)
        } else {
            DareLog.D("이게 뭘까 종료래")
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun initView() {
        requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
                if (isGranted) {
                    DareLog.D("권한 있구요")
                } else {
                    DareLog.D("권한 없구요")
                }
            }

        binding.apply {
            vm = viewModel
            recyclerHome.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = homeAdapter
                itemAnimator = null
            }
            bindRecyclerListener()

            viewModel.getAllProse()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                requestAlarmPermission()
            } else {
                DareLog.D("이게 뭘까 12 이하래")
            }
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.proseList.collect {
                    homeAdapter.submitList(it)
                }
            }
            launch {
                viewModel.eventFlow.collect {
                    sortEvent(it as HomeEvent)
                }
            }
        }
    }

    private fun sortEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.GoToDetailPageEvent -> goToDetailPage(event.proseId, event.proseType)
            is HomeEvent.GoToWriteProseEvent -> goToWriteProsePage()
            is HomeEvent.GoToRecentSearchPageEvent -> goToRecentSearchPage()
            is HomeEvent.ScrollUpEvent -> scrollUp()
            is HomeEvent.GoToDiscussionEvent -> goToDiscussion()
            HomeEvent.GoToMyEvent -> goToMy()
        }
    }

    private fun goToDetailPage(proseId: Int, proseType: DetailType) {
        val action = HomeFragmentDirections.actionHomeToDetail(proseId, proseType)
        findNavController().navigate(action)
    }

    private fun goToWriteProsePage() {
        val action = HomeFragmentDirections.actionHomeToProseWrite(proseId = -1, proseWriteType = WriteType.NEW)
        findNavController().navigate(action)
    }

    private fun goToRecentSearchPage() {
        val action = HomeFragmentDirections.actionHomeToProseRecentSearch(detailType = DetailType.PROSE)
        findNavController().navigate(action)
    }

    private fun bindRecyclerListener() {
        binding.apply {
            recyclerHome.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                    if (lastVisibleItemPosition >= SHOW_SCROLL_UL_ICON_NUM) {
                        binding.imageBtnScrollUp.visibility = View.VISIBLE
                    } else {
                        binding.imageBtnScrollUp.visibility = View.GONE
                    }
                }
            })
        }
    }

    private fun scrollUp() {
        binding.recyclerHome.smoothScrollToPosition(0)
    }

    private fun goToDiscussion() {
        val action = HomeFragmentDirections.actionHomeToDiscussion()
        findNavController().navigate(action)
    }

    private fun goToMy() {
        val action = HomeFragmentDirections.actionHomeToMy()
        findNavController().navigate(action)
    }

    override fun onStart() {
        super.onStart()
    }
}