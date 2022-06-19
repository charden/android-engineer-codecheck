/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.codecheck

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.*
import io.ktor.client.*
import io.ktor.client.engine.android.*
import jp.co.yumemi.android.codecheck.databinding.FragmentSearchBinding

/**
 * 検索画面
 */
class SearchFragment : Fragment(R.layout.fragment_search) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentSearchBinding.bind(view)

        val context = requireContext()
        val viewModel = SearchViewModel(HttpClient(Android))

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
                    val searchResults = viewModel.searchResults(inputText)
                    adapter.submitList(searchResults)

                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }

        binding.recyclerView.also {
            it.layoutManager = layoutManager
            it.addItemDecoration(dividerItemDecoration)
            it.adapter = adapter
        }
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

