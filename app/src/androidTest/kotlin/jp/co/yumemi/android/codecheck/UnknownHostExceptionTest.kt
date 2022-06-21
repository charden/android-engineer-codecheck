package jp.co.yumemi.android.codecheck


import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import jp.co.yumemi.android.codecheck.data.remote.Api
import jp.co.yumemi.android.codecheck.data.remote.ItemRemoteDataSource
import jp.co.yumemi.android.codecheck.data.remote.MockApi
import jp.co.yumemi.android.codecheck.di.NetworkModule
import jp.co.yumemi.android.codecheck.pages.SearchPage
import jp.co.yumemi.android.codecheck.ui.TopActivity
import kotlinx.coroutines.Dispatchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Singleton

@HiltAndroidTest
@UninstallModules(NetworkModule::class)
@LargeTest
@RunWith(AndroidJUnit4::class)
class UnknownHostExceptionTest {

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
            return MockApi(MockApi.Status.UnknownHostException)
        }

        @Provides
        @Singleton
        fun provideRemoteDataSource(api: Api): ItemRemoteDataSource {
            return ItemRemoteDataSource(api, Dispatchers.IO)
        }
    }

    @Test
    fun UnknownHostExceptionが発生したときにerror_networkの文言が表示されること() {
        SearchPage().inputSearchText("kotlin")
            .clickSearch()
            .assertErrorMessage(R.string.error_network)
    }
}
