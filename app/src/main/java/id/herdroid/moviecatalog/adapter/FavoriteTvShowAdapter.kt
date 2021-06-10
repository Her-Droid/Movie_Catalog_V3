package id.herdroid.moviecatalog.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.herdroid.moviecatalog.R
import id.herdroid.moviecatalog.data.entity.TvShowEntity
import id.herdroid.moviecatalog.utils.Constants.IMG_URL
import kotlinx.android.synthetic.main.list_tvshow.view.*

class FavoriteTvShowAdapter internal constructor(
    private val tvShowListener: (TvShowEntity) -> Unit
) : PagedListAdapter<TvShowEntity, FavoriteTvShowAdapter.FavoriteTvShowViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TvShowEntity>() {
            override fun areItemsTheSame(oldItem: TvShowEntity, newItem: TvShowEntity): Boolean {
                return oldItem.tvShowId == newItem.tvShowId
            }

            override fun areContentsTheSame(oldItem: TvShowEntity, newItem: TvShowEntity): Boolean {
                return oldItem == newItem
            }
        }
    }


    class FavoriteTvShowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(tvShow: TvShowEntity, tvShowListener: (TvShowEntity) -> Unit) {
            with(itemView) {
                tv_title.text = tvShow.title
                tv_date.text = tvShow.releaseDate
                tv_description.text = tvShow.description
                Glide.with(itemView.context)
                    .load(IMG_URL + tvShow.imagePath)
                    .into(img_tv)
                setOnClickListener { tvShowListener(tvShow) }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteTvShowViewHolder {
        return FavoriteTvShowViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_tvshow, parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: FavoriteTvShowViewHolder,
        position: Int
    ) {
        val tvShow = getItem(position)
        if (tvShow != null) {
            holder.bind(tvShow, tvShowListener)
        }
    }


}