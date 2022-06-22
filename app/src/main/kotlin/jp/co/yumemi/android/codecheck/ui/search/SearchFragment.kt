/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.codecheck.ui.search

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import jp.co.yumemi.android.codecheck.R
import jp.co.yumemi.android.codecheck.databinding.FragmentSearchBinding
import jp.co.yumemi.android.codecheck.model.Item
import jp.co.yumemi.android.codecheck.ui.ErrorMessage
import kotlinx.coroutines.launch

/**
 * 検索画面
 */
@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private val viewModel: SearchViewModel by viewModels()
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentSearchBinding.bind(view)

        val context = requireContext()
        val layoutManager = LinearLayoutManager(context)
        val dividerItemDecoration =
            DividerItemDecoration(context, layoutManager.orientation)
        val adapter = CustomAdapter(object : CustomAdapter.OnItemClickListener {
            override fun itemClick(item: Item) {
                gotoRepositoryFragment(item)
            }
        })

        binding.searchInputText
            .setOnEditorActionListener { editText, action, _ ->
                if (action == EditorInfo.IME_ACTION_SEARCH) {
                    val inputText = editText.text.toString()
                    viewModel.search(inputText)
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.result.collect { uiState ->
                    when (uiState) {
                        is SearchUiState.Success -> showItem(adapter, uiState.data)
                        is SearchUiState.Failure -> showError(view, uiState.e)
                        is SearchUiState.Loading -> showLoading(view)
                        is SearchUiState.EmptyInput -> showSnackBar(view, getString(R.string.search_error))
                    }
                }
            }
        }

        binding.recyclerView.also {
            it.layoutManager = layoutManager
            it.addItemDecoration(dividerItemDecoration)
            it.adapter = adapter
        }
    }

    private fun hideKeyboard(view: View) {
        val system = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        system?.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun showItem(adapter: CustomAdapter, items: List<Item>) {
        hideLoading()
        adapter.submitList(items)
    }

    private fun showError(view: View, throwable: Throwable) {
        hideLoading()

        showSnackBar(view, getString(ErrorMessage(throwable).getMessageId()))
    }

    private fun showLoading(view: View) {
        hideKeyboard(view)
        binding.loading.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.loading.visibility = View.GONE
    }

    private fun showSnackBar(view: View, message: String) {
        val snackBar = Snackbar.make(
            view,
            message,
            Snackbar.LENGTH_SHORT
        )
        snackBar.show()
    }

    /**
     * レポジトリ詳細画面へ遷移する
     */
    fun gotoRepositoryFragment(item: Item) {
        val action = SearchFragmentDirections
            .actionSearchFragmentToRepositoryFragment(item = item)
        findNavController().navigate(action)
    }
}

