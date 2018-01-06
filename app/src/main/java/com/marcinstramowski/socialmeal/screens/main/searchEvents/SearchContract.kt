package com.marcinstramowski.socialmeal.screens.main.searchEvents

import com.marcinstramowski.socialmeal.screens.base.BaseContract
import com.marcinstramowski.socialmeal.screens.model.SearchResult

/**
 * Contract interfaces between [SearchFragment] and [SearchPresenter]
 */
interface SearchContract {
    interface View : BaseContract.View<Presenter> {
        fun updateSearchResults(searchResults: List<SearchResult>)
    }

    interface Presenter : BaseContract.Presenter {

    }
}