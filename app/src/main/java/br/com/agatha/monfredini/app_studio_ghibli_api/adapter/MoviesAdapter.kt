package br.com.agatha.monfredini.app_studio_ghibli_api.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.agatha.monfredini.app_studio_ghibli_api.R
import br.com.agatha.monfredini.app_studio_ghibli_api.databinding.ItemMovieBinding
import br.com.agatha.monfredini.studio_ghibli_api.model.Movie

class MoviesAdapter(
    private val context: Context,
    private val onClick: (Movie) -> Unit
) : ListAdapter<Movie, MoviesAdapter.MovieViewHolder>(DiffCallback()) {

    inner class MovieViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.image.setBackgroundResource(
                movie.getImage().getImageByDrawable(
                    context,
                    R.drawable.no_image
                )
            )
            binding.title.text = movie.title
            binding.year.text = movie.releaseDate
            binding.root.setOnClickListener { onClick(movie) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie) =
            oldItem.title == newItem.title

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie) = oldItem == newItem
    }
}
