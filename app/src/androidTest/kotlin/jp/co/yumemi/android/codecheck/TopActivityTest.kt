package jp.co.yumemi.android.codecheck


import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import jp.co.yumemi.android.codecheck.pages.SearchPage
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class TopActivityTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(TopActivity::class.java)

    @Test
    fun topActivityTest() {
        SearchPage().inputSearchText("Kotlin")
            .clickSearch()
            .assertRepositoryName(0, "JetBrains/kotlin")
            .clickItem(0)
            .assertDisplayImageView()
            .assertNameView()
            .assertLanguageView()
    }
}
