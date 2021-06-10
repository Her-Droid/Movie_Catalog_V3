package id.herdroid.moviecatalog.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import id.herdroid.moviecatalog.R
import id.herdroid.moviecatalog.enum.TypeData
import id.herdroid.moviecatalog.ui.fragment.FavoriteMovieFragment
import id.herdroid.moviecatalog.ui.fragment.FavoriteTvShowFragment
import id.herdroid.moviecatalog.ui.fragment.MovieFragment
import id.herdroid.moviecatalog.ui.fragment.TvShowFragment

class ViewPagerAdapter(private val context: Context, fragmentManager: FragmentManager) :
    FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> FavoriteMovieFragment()
            1 -> FavoriteTvShowFragment()
            else -> Fragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.getString(
            when (position) {
                0 -> R.string.movie
                1 -> R.string.tv_show
                else -> R.string.empty_movie
            }
        )
    }

}