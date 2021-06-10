package id.herdroid.moviecatalog.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.herdroid.moviecatalog.R
import id.herdroid.moviecatalog.adapter.FavoriteMovieAdapter
import id.herdroid.moviecatalog.data.entity.MovieEntity
import id.herdroid.moviecatalog.ui.detail.DetailActivity
import id.herdroid.moviecatalog.ui.detail.DetailActivity.Companion.EXTRA_MOVIE
import id.herdroid.moviecatalog.viewmodel.MovieViewModel
import id.herdroid.moviecatalog.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_favorite.*

class FavoriteMovieFragment : Fragment() {

    private lateinit var movieViewModel: MovieViewModel
    private lateinit var movieAdapter: FavoriteMovieAdapter

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
            movieViewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]
            initFavorite(movieViewModel)
        }
    }

    private fun initFavorite(movieViewModel: MovieViewModel) {
        pb_favorite_movie.visibility = View.VISIBLE
        movieViewModel.getMovieFavorite().observe(viewLifecycleOwner, Observer { movie ->
            movieAdapter = FavoriteMovieAdapter { item: MovieEntity ->
                getItemClicked(item)
            }
            pb_favorite_movie.visibility = View.GONE
            movieAdapter.submitList(movie)

            with(rv_favorite_movie) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = movieAdapter
            }
        })

    }

    private fun getItemClicked(item: MovieEntity) {
        val intent = Intent(requireActivity(), DetailActivity::class.java)
        intent.putExtra(EXTRA_MOVIE, item.movieId)
        startActivity(intent)
    }

}