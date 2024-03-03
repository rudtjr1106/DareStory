package com.example.darestory.ui.main.search.result

import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.darestory.R
import com.example.darestory.base.BaseFragment
import com.example.darestory.databinding.FragmentResultSearchBinding
import com.example.darestory.ui.common.spinner.SpinnerDialog
import com.example.darestory.ui.main.home.adapter.HomeAdapter
import com.example.darestory.ui.main.search.result.adapter.ResultSearchAdapter
import com.example.domain.model.enums.SearchType
import com.example.domain.model.enums.SortType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ResultSearchFragment : BaseFragment<FragmentResultSearchBinding, ResultSearchPageState, ResultSearchViewModel>(
    FragmentResultSearchBinding::inflate
), SpinnerDialog.OnItemSelectedListener{

    @Inject
    lateinit var spinnerDialog: SpinnerDialog


    override val viewModel: ResultSearchViewModel by viewModels()
    private val resultSearchFragmentArgs : ResultSearchFragmentArgs by navArgs()


    private val resultSearchAdapter : ResultSearchAdapter by lazy {
        ResultSearchAdapter(object : HomeAdapter.HomeDelegate {
            override fun onClickSearch() {}
            override fun onClickProse(proseId: Int) {
                goToDetail(proseId)
            }
            override fun onClickSort(type: SortType) {}
            override fun onClickWriteProse() {}
        })
    }

    override fun initView() {
        binding.apply {
            vm = viewModel
            recyclerResultSearch.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = resultSearchAdapter
            }
            bindEditTextKeyboard()
            textResultSearch.text = getString(R.string.search_result, resultSearchFragmentArgs.searchText)
            viewModel.setViewType(resultSearchFragmentArgs.detailType)
            viewModel.getSearchedList(resultSearchFragmentArgs.searchText)
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.searchResultProseList.collect{
                    updateResultSearchText()
                    resultSearchAdapter.submitList(it)
                }
            }
            launch {
                viewModel.eventFlow.collect {
                    sortEvent(it as ResultSearchEvent)
                }
            }
        }
    }

    private fun sortEvent(event : ResultSearchEvent){
        when(event){
            ResultSearchEvent.GoBackEvent -> findNavController().popBackStack()
            ResultSearchEvent.OnClickSpinnerEvent -> onClickSpinner()
        }
    }

    private fun bindEditTextKeyboard(){
        binding.apply {
            editTextSearch.apply {
                setOnEditorActionListener { _, keyCode, keyEvent ->
                    if(keyCode == EditorInfo.IME_ACTION_SEARCH) {
                        viewModel.insertProseRecentSearch(text.toString())
                        true
                    }else false
                }
            }
        }
    }
    private fun onClickSpinner(){
        val items = resources.getStringArray(R.array.search_type_array)
        spinnerDialog.setItems(items.toList())
        spinnerDialog.setOnItemSelectedListener(this)
        spinnerDialog.show()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onItemSelected(itemName: String) {
        binding.textSpinnerSearchType.text = itemName
        viewModel.updateSearchType(SearchType.valuesOf(itemName))
    }

    private fun updateResultSearchText(){
        binding.textResultSearch.text = viewModel.getSearchText()
    }

    private fun goToDetail(id : Int){
        val action  = ResultSearchFragmentDirections.actionResultToDetail(id, resultSearchFragmentArgs.detailType)
        findNavController().navigate(action)
    }
}