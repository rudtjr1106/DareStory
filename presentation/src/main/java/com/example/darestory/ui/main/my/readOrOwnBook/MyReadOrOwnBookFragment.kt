package com.example.darestory.ui.main.my.readOrOwnBook

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.darestory.R
import com.example.darestory.base.BaseFragment
import com.example.darestory.databinding.FragmentMyReadOrOwnBookBinding
import com.example.darestory.ui.main.my.readOrOwnBook.adapter.MyReadOrOwnBookAdapter
import com.example.darestory.ui.main.search.result.adapter.ResultSearchAdapter
import com.example.domain.model.enums.ReadOrOwnType
import com.example.domain.model.vo.BookVo
import com.example.domain.model.vo.MyBookVo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyReadOrOwnBookFragment : BaseFragment<FragmentMyReadOrOwnBookBinding, MyReadOrOwnBookPageState, MyReadOrOwnBookViewModel>(
    FragmentMyReadOrOwnBookBinding::inflate
) {

    override val viewModel: MyReadOrOwnBookViewModel by viewModels()

    private val myReadOrOwnBookFragmentArgs : MyReadOrOwnBookFragmentArgs by navArgs()

    private val myReadOrOwnBookAdapter : MyReadOrOwnBookAdapter by lazy {
        MyReadOrOwnBookAdapter(object : ResultSearchAdapter.ResultSearchDelegate {
            override fun onClickBook(item: BookVo) {
                TODO("Not yet implemented")
            }
        },

            object : MyReadOrOwnBookAdapter.MyReadOrOwnBookDelegate{
                override fun onClickMyBook(item: MyBookVo) {
                    TODO("Not yet implemented")
                }
            })
    }

    override fun initView() {
        binding.apply {
            vm = viewModel
            recyclerDetail.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = myReadOrOwnBookAdapter
            }
            setTitle(myReadOrOwnBookFragmentArgs.type)
            viewModel.getMyData(myReadOrOwnBookFragmentArgs.type)
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.myReadOrOwnBookList.collect{
                    myReadOrOwnBookAdapter.submitList(it)
                }
            }
            launch {
                viewModel.eventFlow.collect {
                    sortEvent(it as MyReadOrOwnBookEvent)
                }
            }
        }
    }

    private fun sortEvent(event: MyReadOrOwnBookEvent){
        when(event){
            MyReadOrOwnBookEvent.GoToBackEvent -> findNavController().popBackStack()
        }
    }

    private fun setTitle(type : ReadOrOwnType){
        binding.apply {
            val textRes = when(type){
                ReadOrOwnType.READ_BOOK -> R.string.my_read_book
                ReadOrOwnType.OWN_BOOK -> R.string.my_bookmark_prose
            }
            textMyWriteType.text = getString(textRes)
        }
    }

    override fun onStart() {
        super.onStart()
    }
}