package com.example.submission3.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.submission3.R
import com.example.submission3.fragment.FavoriteFragment
import com.example.submission3.fragment.MovieFragment
import com.example.submission3.fragment.TvShowFragment
import com.example.submission3.viewmodel.MovieViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.miguelcatalan.materialsearchview.MaterialSearchView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private var movieFragment = MovieFragment()
    private var fragmentState: String? = null
    private var fragment: Fragment? = null
    private lateinit var movieViewModel: MovieViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar_container)
        toolbar_container.setTitleTextColor(
            ContextCompat.getColor(
                applicationContext,
                R.color.white
            )
        )

        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)

        nav_bottom_menu.setOnNavigationItemSelectedListener(this)

        if (savedInstanceState == null) {
            fragmentState = MovieFragment.TYPE
            loadFragment(movieFragment)
        } else {
            fragment?.let { loadFragment(it) }
        }

        searchMovie()
    }

    private fun searchMovie() {
        search_movie.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                search_movie.closeSearch()
                val intent = Intent(this@MainActivity, SearchMovieActivity::class.java)
                intent.putExtra(SearchMovieActivity.MOVIE_QUERY, query)
                startActivity(intent)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                return false
            }

        })
    }

    private fun loadFragment(fragment: Fragment): Boolean {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
            .commit()
        return true
    }

    override fun onNavigationItemSelected(menu: MenuItem): Boolean {

        when (menu.itemId) {
            R.id.menu_movie -> {
                fragmentState = MovieFragment.TYPE
                fragment = MovieFragment()
            }
            R.id.menu_tv_show -> {
                fragmentState = TvShowFragment.TYPE
                fragment = TvShowFragment()
            }
            R.id.menu_favorite -> {
                fragmentState = FavoriteFragment.TYPE
                fragment = FavoriteFragment()
            }
        }

        return loadFragment(fragment!!)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        val item = menu?.findItem(R.id.action_search)
        search_movie.setMenuItem(item)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.action_change_settings) {
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("saved_state", fragmentState)
    }
}
