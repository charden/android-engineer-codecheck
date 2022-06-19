package jp.co.yumemi.android.codecheck.pages

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import jp.co.yumemi.android.codecheck.R

class RepositoryPage {

    /**
     * ownerIconViewが表示されていることのチェック
     *
     * @return RepositoryPage
     */
    fun assertDisplayImageView(): RepositoryPage {
        val imageView = Espresso.onView(ViewMatchers.withId(R.id.ownerIconView))
        imageView.check(matches(isDisplayed()))
        return this
    }

    /**
     * レポジトリのタイトルが反映されていることのチェック
     *
     * @return RepositoryPage
     */
    fun assertNameView(): RepositoryPage {
        val imageView = Espresso.onView(ViewMatchers.withId(R.id.nameView))
        imageView.check(matches(withText("JetBrains/kotlin")))
        return this
    }

    /**
     * レポジトリの言語が反映されていることのチェック
     *
     * @return RepositoryPage
     */
    fun assertLanguageView(): RepositoryPage {
        val imageView = Espresso.onView(ViewMatchers.withId(R.id.languageView))
        imageView.check(matches(withText("Written in Kotlin")))
        return this
    }
}