package id.herdroid.moviecatalog.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import id.herdroid.moviecatalog.R
import id.herdroid.moviecatalog.data.entity.MovieEntity
import id.herdroid.moviecatalog.data.entity.TvShowEntity
import id.herdroid.moviecatalog.ui.main.MainActivity
import id.herdroid.moviecatalog.utils.Constants
import id.herdroid.moviecatalog.viewmodel.DetailViewModel
import id.herdroid.moviecatalog.viewmodel.ViewModelFactory
import id.herdroid.moviecatalog.vo.Status
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.fragment_tvshow.*

class DetailActivity :AppCompatActivity(){
    private var item: Menu? = null
    private var movieId: Int? = null
    private var tvShowId: Int? = null

    private var movieEntity: MovieEntity? = null
    private var tvShowEntity: TvShowEntity? = null

    private lateinit var viewModel: DetailViewModel

    companion object {
        const val EXTRA_MOVIE = "extra_movie"
        const val EXTRA_TVSHOW = "extra_tvshow"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]

        val extras = intent.extras
        if (extras != null) {
            val movieId = intent.getIntExtra(EXTRA_MOVIE, 0)
            this.movieId = movieId
            if (movieId != 0) {
                viewModel.setSelectedData(movieId)
                progress_barDetail.visibility = View.VISIBLE
                viewModel.getMovie().observe(this, Observer { movie ->
                    if (movie != null) {
                        when (movie.status) {
                            Status.LOADING -> progress_barDetail.visibility = View.VISIBLE
                            Status.SUCCESS -> {
                                progress_barDetail.visibility = View.GONE
                                movieEntity = movie.data
                                movieEntity?.let { populateMovie(it) }
                            }
                            else -> {
                                Status.ERROR
                                progress_barDetail.visibility = View.GONE
                                Toast.makeText(
                                    applicationContext,
                                    getString(R.string.error_msg),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }

                })
            } else  {
                val tvId = intent.getIntExtra(EXTRA_TVSHOW, 0)
                tvShowId = tvId
                if (tvId != 0) {
                    viewModel.setSelectedData(tvId)
                    viewModel.getTvShow().observe(this, Observer { tvShow ->
                        if (tvShow != null) {
                            when (tvShow.status) {
                                Status.LOADING -> progress_barDetail.visibility = View.VISIBLE
                                Status.SUCCESS -> {
                                    progress_barDetail.visibility = View.GONE
                                    tvShowEntity = tvShow.data
                                    tvShowEntity?.let { populateTv(it) }
                                }
                                else -> {
                                    Status.ERROR
                                    progress_barDetail.visibility = View.GONE
                                    Toast.makeText(
                                        applicationContext,
                                        getString(R.string.error_msg),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    })
                }
            }
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun populateMovie(data: MovieEntity) {
        movie_title.text = data.title
        movie_detail.text = data.description
        release_date.text = data.releaseDate
        Glide.with(this).load(Constants.IMG_URL + data.imagePath)
            .into(bg_image)
        Glide.with(this).load(Constants.IMG_URL + data.imagePath)
            .into(imgPoster)
        title = data.title
    }

    private fun populateTv(data: TvShowEntity) {
        movie_title.text = data.title
        movie_detail.text = data.description
        release_date.text = data.releaseDate
        Glide.with(this).load(Constants.IMG_URL + data.imagePath)
            .into(bg_image)
        Glide.with(this).load(Constants.IMG_URL + data.imagePath)
            .into(imgPoster)

        title = data.title
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        this.item = menu
        if (movieId != null) {
            viewModel.getMovie().observe(this, Observer { movie ->
                if (movie != null) {
                    when (movie.status){
                        Status.LOADING -> progress_barDetail.visibility = View.VISIBLE
                        Status.SUCCESS -> if (movie.data != null){
                            progress_barDetail.visibility = View.GONE
                            val state = movie.data.favorite
                            setFavoriteState(state)
                        } else -> {
                        Status.ERROR
                        pb_tv.visibility = View.GONE
                        Toast.makeText(applicationContext,getString(R.string.error_msg), Toast.LENGTH_SHORT).show()
                    }
                    }
                }
            })
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.button_favorite) {
            if (movieId != null) {
                movieEntity?.let {
                    viewModel.setFavoriteMovie(it)
                    if (it.favorite) {
                        Toast.makeText(this, "Removed from favorite movie", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Added to favorite movie", Toast.LENGTH_SHORT).show()
                    }
                }
                return true
            } else if (tvShowId != null) {
                tvShowEntity?.let {
                    viewModel.setFavoriteTvShow(it)
                    if (it.favorite) {
                        Toast.makeText(this, "Removed from favorite tv", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Added to favorite tv", Toast.LENGTH_SHORT).show()
                    }
                }

                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun setFavoriteState(state: Boolean) {
        if (item == null) return
        val menuItem = item?.findItem(R.id.button_favorite)
        if (state) {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_baseline_favorite_24)
        } else {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_baseline_favorite_border_24)
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}