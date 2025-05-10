package com.mg_group.womniz.Ui.ActivityHome.FragmentHome.Utils


import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.khalil.paymobtask.R
import com.khalil.paymobtask.data.datasource.models.Movie
import com.khalil.paymobtask.databinding.ItemMoviesBinding
import com.khalil.paymobtask.utils.Constants


class MoviesAdapter(
    private val onFavoriteClick: (Movie) -> Unit,
    private val onMovieClick: (Movie) -> Unit
) : PagingDataAdapter<Movie, MoviesAdapter.MoviesHolder>(MovieDiffCallback()) {

    inner class MoviesHolder(
        val binding: ItemMoviesBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            with(binding) {
                textTitle.text = movie.title
                textRate.text = String.format("%.1f", movie.vote_average)
                textDate.text = movie.release_date

                val heartColor = if (movie.isFavorite) R.color.red else R.color.gray
                iconHeart.setColorFilter(
                    ContextCompat.getColor(itemView.context, heartColor),
                    PorterDuff.Mode.SRC_IN
                )

                Glide.with(itemView.context)
                    .load(Constants.IMG_URL + movie.poster_path)
                    .into(imageMovie)

                iconHeart.setOnClickListener {
                    onFavoriteClick(movie)
                }
                root.setOnClickListener {
                    onMovieClick(movie)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesHolder {
        val binding = ItemMoviesBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MoviesHolder(binding)
    }

    override fun onBindViewHolder(holder: MoviesHolder, position: Int) {
        getItem(position)?.let { movie ->
            holder.bind(movie)
        }
    }
}



class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem == newItem
}