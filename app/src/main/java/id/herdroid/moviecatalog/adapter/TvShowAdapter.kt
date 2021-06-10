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
import id.herdroid.moviecatalog.data.entity.TvShowEntity
import id.herdroid.moviecatalog.ui.detail.DetailActivity
import id.herdroid.moviecatalog.utils.Constants.IMG_URL
import kotlinx.android.synthetic.main.list_tvshow.view.*

class TvShowAdapter: PagedListAdapter<TvShowEntity, TvShowAdapter.ViewHolder>(TV_CALLBACK) {

    companion object {
        private val TV_CALLBACK = object: DiffUtil.ItemCallback<TvShowEntity>() {
            override fun areItemsTheSame(oldItem: TvShowEntity, newItem: TvShowEntity): Boolean {
                return oldItem.tvShowId == newItem.tvShowId
            }

            override fun areContentsTheSame(oldItem: TvShowEntity, newItem: TvShowEntity): Boolean {
                return oldItem == newItem
            }
        }
    }



    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindItem(tvShow: TvShowEntity) {
            itemView.run {
                tvShow.also {
                    Glide.with(itemView.context)
                        .load(IMG_URL + tvShow.imagePath)
                        .into(img_tv)
                    tv_title.text = it.title
                    tv_description.text = it.description
                    tv_date.text =
                        itemView.resources.getString(R.string.release_date, tvShow.releaseDate)
                }
                setOnClickListener {
                   context.startActivity(
                        Intent(context, DetailActivity::class.java)
                            .putExtra(DetailActivity.EXTRA_TVSHOW, tvShow.tvShowId)
                    )
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_tvshow, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tvShow = getItem(position)
        if (tvShow != null) {
            holder.bindItem(tvShow)
        }
    }

}

