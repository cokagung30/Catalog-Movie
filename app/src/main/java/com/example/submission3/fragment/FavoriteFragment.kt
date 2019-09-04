package com.example.submission3.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.submission3.R
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems
import kotlinx.android.synthetic.main.fragment_favorite.*

class FavoriteFragment : Fragment() {

    companion object {
        const val TYPE = "Favorite"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val page = FragmentPagerItems(view.context)
        page.add(FragmentPagerItem.of(getString(R.string.movie_title), FavoriteMovieFragment::class.java))
        page.add(FragmentPagerItem.of(getString(R.string.tv_show_title), FavoriteTvShowFragment::class.java))

        val adapterFragment = FragmentPagerItemAdapter(childFragmentManager, page)
        view_pager.adapter = adapterFragment
        view_pager_tab.setViewPager(view_pager)

    }

}