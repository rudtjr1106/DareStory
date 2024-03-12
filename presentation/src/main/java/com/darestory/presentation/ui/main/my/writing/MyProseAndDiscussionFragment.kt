package com.darestory.presentation.ui.main.my.writing

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.darestory.presentation.R
import com.darestory.presentation.base.BaseFragment
import com.darestory.presentation.databinding.FragmentMyProseAndDiscussionBinding
import com.darestory.presentation.ui.common.CommonBottomSheet
import com.darestory.presentation.ui.common.CommonDialog
import com.darestory.presentation.ui.main.discussion.adapter.DiscussionAdapter
import com.darestory.presentation.ui.main.home.adapter.HomeAdapter
import com.darestory.presentation.ui.main.my.writing.adapter.MyProseAndDiscussionAdapter
import com.darestory.domain.model.enums.BottomSheetType
import com.darestory.domain.model.enums.DetailType
import com.darestory.domain.model.enums.SortType
import com.darestory.domain.model.enums.WriteType
import com.darestory.domain.model.vo.ProseVo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MyProseAndDiscussionFragment : BaseFragment<FragmentMyProseAndDiscussionBinding, MyProseAndDiscussionPageState, MyProseAndDiscussionViewModel>(
    FragmentMyProseAndDiscussionBinding::inflate
) {

    @Inject
    lateinit var commonDialog: CommonDialog

    override val viewModel: MyProseAndDiscussionViewModel by viewModels()

    private val myProseAndDiscussionFragmentArgs : MyProseAndDiscussionFragmentArgs by navArgs()

    private val myProseAndDiscussionAdapter : MyProseAndDiscussionAdapter by lazy {
        MyProseAndDiscussionAdapter(object : HomeAdapter.HomeDelegate {
            override fun onClickSearch() {}
            override fun onClickProse(proseId: Int) {
                goToDetail(proseId)
            }
            override fun onClickSort(type: SortType) {}
            override fun onClickWriteProse() {}
        },
            object : MyProseAndDiscussionAdapter.MyProseAndDiscussionDelegate{
                override fun onClickMenu(item: ProseVo) {
                    showBottomSheet(BottomSheetType.COMMENT_WRITER, item)
                }

                override fun onClickProse(id: Int) {
                    goToDetail(id)
                }

            },
            object : DiscussionAdapter.DiscussionDelegate{
                override fun onClickSearch() {}
                override fun onClickDiscussion(disId: Int) {
                    goToDetail(disId)
                }
                override fun onClickSort(type: SortType) {}
                override fun onClickWriteDiscussion() {}

            })
    }

    override fun initView() {
        binding.apply {
            vm = viewModel
            recyclerDetail.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = myProseAndDiscussionAdapter
            }
            setTitle(myProseAndDiscussionFragmentArgs.type, myProseAndDiscussionFragmentArgs.ownBookName)
            viewModel.getMyData(myProseAndDiscussionFragmentArgs.type, myProseAndDiscussionFragmentArgs.ownBookName)
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.myWritingList.collect{
                    myProseAndDiscussionAdapter.submitList(it)
                }
            }
            launch {
                viewModel.eventFlow.collect {
                    sortEvent(it as MyProseAndDiscussionEvent)
                }
            }
        }
    }

    private fun sortEvent(event: MyProseAndDiscussionEvent){
        when(event){
            MyProseAndDiscussionEvent.GoToBackEvent -> findNavController().popBackStack()
            MyProseAndDiscussionEvent.GoToDiscussionWriteEvent -> showGoToDiscussionWriteDialog()
            MyProseAndDiscussionEvent.GoToProseWriteEvent -> showGoToProseWriteDialog()
        }
    }

    private fun showGoToDiscussionWriteDialog(){
        commonDialog
            .setTitle(R.string.dialog_my_discussion_empty_title)
            .setDescription(R.string.dialog_my_discussion_empty_content)
            .setNegativeButton(R.string.word_cancel){
                commonDialog.dismiss()
            }
            .setPositiveButton(R.string.word_confirm){
                goToDiscussionWritePage()
                commonDialog.dismiss()
            }
            .show()
    }

    private fun showGoToProseWriteDialog(){
        commonDialog
            .setTitle(R.string.dialog_my_prose_empty_title)
            .setDescription(R.string.dialog_my_prose_empty_content)
            .setNegativeButton(R.string.word_cancel){
                commonDialog.dismiss()
            }
            .setPositiveButton(R.string.word_confirm){
                goToProseWritePage()
                commonDialog.dismiss()
            }
            .show()
    }

    private fun goToDiscussionWritePage(){
        val action = MyProseAndDiscussionFragmentDirections.actionMyProseAndDiscussionWrite(0, WriteType.NEW)
        findNavController().navigate(action)
    }

    private fun goToProseWritePage(){
        val action = MyProseAndDiscussionFragmentDirections.actionMyProseAndProseWrite(0, WriteType.NEW)
        findNavController().navigate(action)
    }

    private fun setTitle(type : DetailType, ownBookTitle : String){
        binding.apply {
            val text = when(type){
                DetailType.PROSE -> getString(R.string.my_prose)
                DetailType.DISCUSSION -> getString(R.string.my_discussion)
                DetailType.BOOK -> ownBookTitle
            }
            textMyWriteType.text = text
        }
    }

    private fun goToDetail(id : Int){
        val action = if(myProseAndDiscussionFragmentArgs.type == DetailType.BOOK){
            MyProseAndDiscussionFragmentDirections.actionMyProseAndDiscussionToDetail(id, DetailType.PROSE)
        }
        else{
            MyProseAndDiscussionFragmentDirections.actionMyProseAndDiscussionToDetail(id, myProseAndDiscussionFragmentArgs.type)
        }
        findNavController().navigate(action)
    }

    private fun showBottomSheet(type : BottomSheetType, item : ProseVo){
        CommonBottomSheet.newInstance(type) {
            viewModel.onClickImageMenuItemType(it, item)
        }.show(parentFragmentManager, "")
    }

    override fun onStart() {
        super.onStart()
    }
}