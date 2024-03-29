package com.darestory.presentation.ui.main.my.readOrOwnBook

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.darestory.presentation.R
import com.darestory.presentation.base.BaseFragment
import com.darestory.presentation.databinding.FragmentMyReadOrOwnBookBinding
import com.darestory.presentation.ui.common.BookDetailDialog
import com.darestory.presentation.ui.main.my.readOrOwnBook.adapter.MyReadOrOwnBookAdapter
import com.darestory.presentation.ui.main.search.result.adapter.ResultSearchAdapter
import com.darestory.presentation.util.DareToast
import com.darestory.presentation.util.SelectedBook
import com.darestory.domain.model.enums.DetailType
import com.darestory.domain.model.enums.ReadOrOwnType
import com.darestory.domain.model.enums.ToastType
import com.darestory.domain.model.vo.BookVo
import com.darestory.domain.model.vo.MyBookVo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MyReadOrOwnBookFragment : BaseFragment<FragmentMyReadOrOwnBookBinding, MyReadOrOwnBookPageState, MyReadOrOwnBookViewModel>(
    FragmentMyReadOrOwnBookBinding::inflate
) {

    @Inject
    lateinit var bookDetailDialog: BookDetailDialog

    override val viewModel: MyReadOrOwnBookViewModel by viewModels()

    private val myReadOrOwnBookFragmentArgs : MyReadOrOwnBookFragmentArgs by navArgs()

    private val myReadOrOwnBookAdapter : MyReadOrOwnBookAdapter by lazy {
        MyReadOrOwnBookAdapter(object : ResultSearchAdapter.ResultSearchDelegate {
            override fun onClickBook(item: BookVo) {
                showBookDetailDialog(item)
            }
        },

            object : MyReadOrOwnBookAdapter.MyReadOrOwnBookDelegate{
                override fun onClickMyBook(item: MyBookVo) {
                    when(myReadOrOwnBookFragmentArgs.type){
                        ReadOrOwnType.READ_BOOK -> {}
                        ReadOrOwnType.OWN_BOOK -> goToMyProseAndDiscussion(item.myBookTitle)
                        ReadOrOwnType.SELECT_OWN_BOOK -> viewModel.updateSelectedBook(item)
                    }
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
            MyReadOrOwnBookEvent.GoToResultSearchEvent -> goToResultSearchBook()
            MyReadOrOwnBookEvent.GoToMyOwnBookWriteEvent -> goToMyOwnBookWrite()
            MyReadOrOwnBookEvent.ErrorUploadEvent -> showUploadErrorToast()
        }
    }

    private fun setTitle(type : ReadOrOwnType){
        binding.apply {
            val textRes = when(type){
                ReadOrOwnType.READ_BOOK -> R.string.my_read_book
                ReadOrOwnType.OWN_BOOK -> R.string.my_bookmark_prose
                ReadOrOwnType.SELECT_OWN_BOOK -> R.string.my_bookmark_prose
            }
            textMyWriteType.text = getString(textRes)
        }
    }

    private fun goToResultSearchBook(){
        val action = MyReadOrOwnBookFragmentDirections.actionMyReadOrOwnBookToResultSearch(detailType = DetailType.BOOK)
        findNavController().navigate(action)
    }

    private fun goToMyProseAndDiscussion(title : String){
        val action = MyReadOrOwnBookFragmentDirections.actionMyReadOrOwnBookToMyProseAndDiscussion(DetailType.BOOK, title)
        findNavController().navigate(action)
    }

    private fun goToMyOwnBookWrite(){
        val action = MyReadOrOwnBookFragmentDirections.actionMyReadOrOwnBookToMyOwnBookWrite()
        findNavController().navigate(action)
    }

    private fun showUploadErrorToast(){
        DareToast.createToast(ToastType.ERROR, requireContext(), R.string.toast_error_book_upload_fail).show()
    }

    private fun showBookDetailDialog(item : BookVo){
        bookDetailDialog
            .setImage(item.image)
            .setTitle(item.title)
            .setAuthor(item.author)
            .setPublisher(item.publisher)
            .setDescription(item.description)
            .setNegativeButton {
                bookDetailDialog.dismiss()
            }
            .setPositiveButton {
                bookDetailDialog.dismiss()
            }
            .show()
    }


    override fun onResume() {
        if(SelectedBook.book.title.isNotEmpty()){
            viewModel.addMyReadBook()
        }
        super.onResume()
    }

    override fun onStart() {
        super.onStart()
    }
}