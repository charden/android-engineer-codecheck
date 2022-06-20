/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.codecheck.ui.repository

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import jp.co.yumemi.android.codecheck.R
import jp.co.yumemi.android.codecheck.databinding.FragmentRepositoryBinding

/**
 * レポジトリの詳細画面
 */
class RepositoryFragment : Fragment(R.layout.fragment_repository) {

    private val args: RepositoryFragmentArgs by navArgs()

    private var binding: FragmentRepositoryBinding? = null
    private val _binding get() = binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRepositoryBinding.bind(view)

        val item = args.item

        _binding.ownerIconView.load(item.ownerIconUrl)
        _binding.nameView.text = item.name
        _binding.languageView.text = getString(R.string.written_language, item.language)
        _binding.starsView.text = getString(R.string.stars, item.stargazersCount)
        _binding.watchersView.text = getString(R.string.watchers, item.watchersCount)
        _binding.forksView.text = getString(R.string.forks, item.forksCount)
        _binding.openIssuesView.text = getString(R.string.open_issues, item.openIssuesCount)
    }
}
