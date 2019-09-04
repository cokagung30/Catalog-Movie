package com.example.submission3.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.submission3.R
import com.example.submission3.fragment.SearchMovieFragment
import com.example.submission3.fragment.SearchTvShowFragment
import com.miguelcatalan.materialsearchview.MaterialSearchView
import com.ogaclejapan.smarttablayout.utils.v4.Bundler
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems
import kotlinx.android.synthetic.main.activity_search_movie.*

class SearchMovieActivity : AppCompatActivity() {

    companion object {
        const val MOVIE_QUERY = "query"
    }

    private var query: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_movie)

        query = intent.getStringExtra(MOVIE_QUERY)
        toolbar_search_movie.title = query
        setSupportActionBar(toolbar_search_movie)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar_search_movie.setTitleTextColor(
            ContextCompat.getColor(
                applicationContext,
                R.color.white
            )
        )

        toolbar_search_movie.setNavigationOnClickListener {
            super.onBackPressed()
        }

        val page = FragmentPagerItems(this)
        page.add(
            FragmentPagerItem.of(
                getString(R.string.movie_title),
                SearchMovieFragment::class.java,
                Bundler().putString(SearchMovieFragment.NAME_MOVIE, query).get()
            )
        )
        page.add(
            FragmentPagerItem.of(
                getString(R.string.tv_show_title),
                SearchTvShowFragment::class.java,
                Bundler().putString(SearchTvShowFragment.NAME_MOVIE, query).get()
            )
        )

        val adapterFragment = FragmentPagerItemAdapter(supportFragmentManager, page)
        view_pager_search.adapter = adapterFragment
        view_pager_tab_search.setViewPager(view_pager_search)

        searchMovie()
    }

    private fun searchMovie() {
        search_movie_result.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val intent = Intent(this@SearchMovieActivity, SearchMovieActivity::class.java)
                intent.putExtra(MOVIE_QUERY, query)
                startActivity(intent)
                finish()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                return false
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        val item = menu?.findItem(R.id.action_search_movie)
        search_movie_result.setMenuItem(item)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onBackPressed() {
        if (search_movie_result.isSearchOpen) {
            search_movie_result.closeSearch()
        } else {
            super.onBackPressed()
        }
    }
}
