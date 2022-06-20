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

    /**
     * star数が反映されていることのチェック
     *
     * @return RepositoryPage
     */
    fun assertStarsView(): RepositoryPage {
        val imageView = Espresso.onView(ViewMatchers.withId(R.id.starsView))
        imageView.check(matches(withText("41802 stars")))
        return this
    }

    /**
     * watcher数が反映されていることのチェック
     *
     * @return RepositoryPage
     */
    fun assertWatchersView(): RepositoryPage {
        val imageView = Espresso.onView(ViewMatchers.withId(R.id.watchersView))
        imageView.check(matches(withText("41802 watchers")))
        return this
    }

    /**
     * fork数が反映されていることのチェック
     *
     * @return RepositoryPage
     */
    fun assertForksView(): RepositoryPage {
        val imageView = Espresso.onView(ViewMatchers.withId(R.id.forksView))
        imageView.check(matches(withText("5162 forks")))
        return this
    }

    /**
     * open issue数が反映されていることのチェック
     *
     * @return RepositoryPage
     */
    fun assertOpenIssuesView(): RepositoryPage {
        val imageView = Espresso.onView(ViewMatchers.withId(R.id.openIssuesView))
        imageView.check(matches(withText("147 open issues")))
        return this
    }
}