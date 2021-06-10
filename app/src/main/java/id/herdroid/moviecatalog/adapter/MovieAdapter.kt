package id.herdroid.moviecatalog.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.herdroid.moviecatalog.R
import id.herdroid.moviecatalog.data.entity.MovieEntity
import id.herdroid.moviecatalog.ui.detail.DetailActivity
import id.herdroid.moviecatalog.utils.Constants.IMG_URL
import kotlinx.android.synthetic.main.list_movie.view.*

class MovieAdapter : PagedListAdapter<MovieEntity, MovieAdapter.ViewHolder>(MOVIE_CALLBACK) {

    private var movies: MutableList<MovieEntity> = mutableListOf()

    companion object {
        private val MOVIE_CALLBACK = object : DiffUtil.ItemCallback<MovieEntity>() {
            override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem.movieId == newItem.movieId
            }

            override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem == newItem
            }

        }
    }

    fun setMovie(movieEntity: List<MovieEntity>) {
        movies.clear()
        movies.addAll(movieEntity)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindItem(movies: MovieEntity) {
            itemView.run {
                movies.also {
                    Glide.with(itemView.context)
                        .load(IMG_URL + it.imagePath)
                        .into(img_movie)
                    movie_title.text = it.title
                    movie_description.text = it.description
                    movie_date.text =
                        itemView.resources.getString(R.string.release_date, movies.releaseDate)
                }
                setOnClickListener {
                    context.startActivity(
                        Intent(context, DetailActivity::class.java)
                            .putExtra(DetailActivity.EXTRA_MOVIE, movies.movieId)
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_movie, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movies = getItem(position)
        if (movies != null) {
            holder.bindItem(movies)
        }
    }
}

