package id.herdroid.moviecatalog.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.herdroid.moviecatalog.R
import id.herdroid.moviecatalog.adapter.FavoriteTvShowAdapter
import id.herdroid.moviecatalog.data.entity.TvShowEntity
import id.herdroid.moviecatalog.ui.detail.DetailActivity
import id.herdroid.moviecatalog.ui.detail.DetailActivity.Companion.EXTRA_TVSHOW
import id.herdroid.moviecatalog.viewmodel.TvShowViewModel
import id.herdroid.moviecatalog.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_favorite.*

class FavoriteTvShowFragment: Fragment() {

    private lateinit var tvShowViewModel: TvShowViewModel
    private lateinit var tvShowAdapter: FavoriteTvShowAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            tvShowViewModel = ViewModelProvider(this, factory)[TvShowViewModel::class.java]
            initFavorite(tvShowViewModel)
        }
    }

    private fun initFavorite(tvShowViewModel: TvShowViewModel) {
        pb_favorite_movie.visibility = View.VISIBLE
        tvShowViewModel.getMovieFavorite().observe(viewLifecycleOwner, Observer { tvShow ->
            if (tvShow != null) {
                val tvShowAdapter = FavoriteTvShowAdapter{
                    item: TvShowEntity -> getItemClicked(item) }

                pb_favorite_movie.visibility = View.GONE
                tvShowAdapter.submitList(tvShow)

                with(rv_favorite_movie){
                    layoutManager = LinearLayoutManager(context)
                    setHasFixedSize(true)
                    adapter = tvShowAdapter
                }
            }
        })
    }

    private fun getItemClicked(item: TvShowEntity) {
        val intent = Intent(requireActivity(), DetailActivity::class.java)
        intent.putExtra(EXTRA_TVSHOW, item.tvShowId)
        startActivity(intent)
    }


}