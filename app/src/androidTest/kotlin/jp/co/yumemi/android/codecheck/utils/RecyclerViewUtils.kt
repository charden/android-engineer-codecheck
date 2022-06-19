package jp.co.yumemi.android.codecheck.utils

import android.view.View
import android.view.ViewParent
import androidx.recyclerview.widget.RecyclerView
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

object RecyclerViewUtils {
    /**
     * RecyclerViewのpositionに対するitemViewを取得する
     */
    fun withItemViewAtPosition(
        recyclerViewMatcher: Matcher<View?>,
        position: Int
    ): Matcher<View?> {
        return object : TypeSafeMatcher<View?>() {
            override fun matchesSafely(view: View?): Boolean {
                val parent: ViewParent? = view?.parent
                if ((parent !is RecyclerView) || !recyclerViewMatcher.matches(parent)) {
                    return false
                }
                val viewHolder = parent.findViewHolderForAdapterPosition(position)
                return viewHolder != null && viewHolder.itemView == view
            }

            override fun describeTo(description: Description) {
                description.appendText("Item view at position $position in recycler view ")
                recyclerViewMatcher.describeTo(description)
            }

        }
    }
}