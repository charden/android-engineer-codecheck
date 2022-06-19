package jp.co.yumemi.android.codecheck.pages

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import jp.co.yumemi.android.codecheck.CustomAdapter
import jp.co.yumemi.android.codecheck.R
import jp.co.yumemi.android.codecheck.utils.RecyclerViewUtils.withItemViewAtPosition
import org.hamcrest.core.AllOf.allOf


class SearchPage {

    /**
     * 検索文字列を入力する
     *
     * @param searchText String 検索する文字列
     * @return SearchPage
     */
    fun inputSearchText(searchText: String): SearchPage {
        onView(withId(R.id.searchInputText))
            .perform(ViewActions.replaceText(searchText))
        return this
    }

    /**
     * キーボードの検索アクションを実行する
     *
     * @return SearchPage
     */
    fun clickSearch(): SearchPage {
        onView(withId(R.id.searchInputText)).perform(ViewActions.pressImeActionButton())
        return this
    }

    /**
     * 検索結果として表示されたRecyclerViewの位置に対する文字列をチェックする
     *
     * @param position Int RecyclerViewの位置
     * @param text String マッチする文字列
     *
     * @return SearchPage
     */
    fun assetRepositoryName(position: Int, text: String): SearchPage {
        onView(
            allOf
                (
                isDescendantOfA(
                    withItemViewAtPosition(
                        withId(R.id.recyclerView),
                        position
                    )
                ),
                withId(R.id.repositoryNameView)
            )
        )
            .check(matches(withText(text)))
        return this
    }

    /**
     * RecyclerViewの指定した位置をクリックする
     *
     * @param position Int クリックするRecyclerViewのPosition
     */
    fun clickItem(position: Int): RepositoryPage {
        onView(withId(R.id.recyclerView)).perform(
            actionOnItemAtPosition<CustomAdapter.ViewHolder>(
                position,
                click()
            )
        )
        return RepositoryPage()
    }


}