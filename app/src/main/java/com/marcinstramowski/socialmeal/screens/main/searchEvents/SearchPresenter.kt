package com.marcinstramowski.socialmeal.screens.main.searchEvents

import com.marcinstramowski.socialmeal.screens.model.SearchResult
import javax.inject.Inject

/**
 * Search event screen logic
 */
class SearchPresenter @Inject constructor(
        private val view: SearchContract.View
) : SearchContract.Presenter {

    override fun onCreate() {
        view.updateSearchResults(getMockSearchResults())
    }

    override fun onDestroy() {

    }

    private fun getMockSearchResults(): List<SearchResult> {
        return listOf(
                SearchResult("Event 1", "Italian"),
                SearchResult("Event 2", "Polish"),
                SearchResult("Event 3", "Chinese"),
                SearchResult("Event 4", "Japanese"),
                SearchResult("Event 5", "Egyptian"),
                SearchResult("Event 6", "Spanish"),
                SearchResult("Event 7", "French")
        )
    }
}