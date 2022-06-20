package jp.co.yumemi.android.codecheck


import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.util.network.*
import jp.co.yumemi.android.codecheck.di.NetworkModule
import jp.co.yumemi.android.codecheck.pages.SearchPage
import kotlinx.coroutines.Dispatchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Singleton

@HiltAndroidTest
@UninstallModules(NetworkModule::class)
@LargeTest
@RunWith(AndroidJUnit4::class)
class TopActivityTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @Rule(order = 1)
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(TopActivity::class.java)

    @Module
    @InstallIn(SingletonComponent::class)
    object NetworkModule {
        @Provides
        @Singleton
        fun provideApi(): Api {
            return MockApi()
        }

        @Provides
        @Singleton
        fun provideRemoteDataSource(api: Api): ItemRemoteDataSource {
            return ItemRemoteDataSource(api, Dispatchers.IO)
        }
    }

    @Test
    fun testSearchKotlin() {
        SearchPage().inputSearchText("Kotlin")
            .clickSearch()
            .assertRepositoryName(0, "JetBrains/kotlin")
            .clickItem(0)
            .assertDisplayImageView()
            .assertNameView()
            .assertLanguageView()
            .assertStarsView()
            .assertWatchersView()
            .assertForksView()
            .assertOpenIssuesView()
    }

    @Test
    fun testSearchEmptyString() {
        SearchPage().inputSearchText("")
            .clickSearch()
            .assertRecyclerViewSize(0)
    }
}
