/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.codecheck.ui.repository

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import android.view.View
import androidx.annotation.StringRes
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
        binding.starsView.text = spannableString(R.string.stars,item.stargazersCount)
        binding.watchersView.text = spannableString(R.string.watchers, item.watchersCount)
        binding.forksView.text = spannableString(R.string.forks, item.forksCount)
        binding.openIssuesView.text = spannableString(R.string.open_issues, item.openIssuesCount)
    }

    private fun spannableString( @StringRes resId: Int,count: Long): SpannableStringBuilder =
        SpannableStringBuilder().apply {
            append(count.toString())
            val start: Int = length
            append(getString(resId))
            setSpan(RelativeSizeSpan(2.0f), 0, start, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

}
