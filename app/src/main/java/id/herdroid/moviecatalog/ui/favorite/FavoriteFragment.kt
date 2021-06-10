package id.herdroid.moviecatalog.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import id.herdroid.moviecatalog.R
import id.herdroid.moviecatalog.adapter.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_favorite.*

class FavoriteFragment : Fragment() {

    private lateinit var vAdapter: ViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vAdapter = ViewPagerAdapter(requireActivity(), requireActivity().supportFragmentManager)
        vp_favorite.adapter = vAdapter
        tab_favorite.setupWithViewPager(vp_favorite)
    }

}