package com.marcinstramowski.socialmeal.screens.main.searchEvents

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.github.nitrico.lastadapter.LastAdapter
import com.marcinstramowski.socialmeal.BR
import com.marcinstramowski.socialmeal.R
import com.marcinstramowski.socialmeal.databinding.ItemSearchResultBinding
import com.marcinstramowski.socialmeal.screens.base.BaseFragment
import com.marcinstramowski.socialmeal.screens.model.SearchResult
import kotlinx.android.synthetic.main.fragment_search.*
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.toast
import javax.inject.Inject

/**
 * Screen that allows user to search and sign for other people events
 */
class SearchFragment : BaseFragment<SearchContract.Presenter>(), SearchContract.View {

    @Inject override lateinit var presenter: SearchContract.Presenter
    override val contentViewId = R.layout.fragment_search

    override fun onCreated(savedInstanceState: Bundle?) {
        configureRecyclerView()
    }

    private fun configureRecyclerView() {
        searchResultList.layoutManager = LinearLayoutManager(context)
        searchResultList.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }

    override fun updateSearchResults(searchResults: List<SearchResult>) {
        LastAdapter(searchResults, BR.searchResult)
                .map<SearchResult, ItemSearchResultBinding>(R.layout.item_search_result) {
                    onClick { activity?.toast(it.binding.eventName.text) }
                }
                .into(searchResultList)
    }
}