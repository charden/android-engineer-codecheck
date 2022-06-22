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
        val ownerIconView = Espresso.onView(ViewMatchers.withId(R.id.ownerIconView))
        ownerIconView.check(matches(isDisplayed()))
        return this
    }

    /**
     * レポジトリのタイトルが反映されていることのチェック
     *
     * @return RepositoryPage
     */
    fun assertNameView(): RepositoryPage {
        val nameView = Espresso.onView(ViewMatchers.withId(R.id.nameView))
        nameView.check(matches(withText("JetBrains/kotlin")))
        return this
    }

    /**
     * レポジトリの言語が反映されていることのチェック
     *
     * @return RepositoryPage
     */
    fun assertLanguageView(): RepositoryPage {
        val languageView = Espresso.onView(ViewMatchers.withId(R.id.languageView))
        languageView.check(matches(withText("Written in Kotlin")))
        return this
    }

    /**
     * star数が反映されていることのチェック
     *
     * @return RepositoryPage
     */
    fun assertStarsView(): RepositoryPage {
        val starsView = Espresso.onView(ViewMatchers.withId(R.id.starsView))
        starsView.check(matches(withText("41802\n stars")))
        return this
    }

    /**
     * watcher数が反映されていることのチェック
     *
     * @return RepositoryPage
     */
    fun assertWatchersView(): RepositoryPage {
        val watchersView = Espresso.onView(ViewMatchers.withId(R.id.watchersView))
        watchersView.check(matches(withText("41802\n watchers")))
        return this
    }

    /**
     * fork数が反映されていることのチェック
     *
     * @return RepositoryPage
     */
    fun assertForksView(): RepositoryPage {
        val forksView = Espresso.onView(ViewMatchers.withId(R.id.forksView))
        forksView.check(matches(withText("5162\n forks")))
        return this
    }

    /**
     * open issue数が反映されていることのチェック
     *
     * @return RepositoryPage
     */
    fun assertOpenIssuesView(): RepositoryPage {
        val openIssuesView = Espresso.onView(ViewMatchers.withId(R.id.openIssuesView))
        openIssuesView.check(matches(withText("147\n open issues")))
        return this
    }
}