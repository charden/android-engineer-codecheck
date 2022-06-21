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

    private var _binding: FragmentRepositoryBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentRepositoryBinding.bind(view)

        val item = args.item

        binding.ownerIconView.load(item.ownerIconUrl)
        binding.nameView.text = item.name
        binding.languageView.text = getString(R.string.written_language, item.language)
        binding.starsView.text = getString(R.string.stars, item.stargazersCount)
        binding.watchersView.text = getString(R.string.watchers, item.watchersCount)
        binding.forksView.text = getString(R.string.forks, item.forksCount)
        binding.openIssuesView.text = getString(R.string.open_issues, item.openIssuesCount)
    }
}
