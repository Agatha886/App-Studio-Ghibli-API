package br.com.agatha.monfredini.app_studio_ghibli_api.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.agatha.monfredini.app_studio_ghibli_api.databinding.ItemCharacterBinding
import br.com.agatha.monfredini.studio_ghibli_api.model.Location

class ListLocationsAdapter(
    private val onClick: (Location) -> Unit
) :
    ListAdapter<Location, ListLocationsAdapter.ListLocationsViewHolder>(DiffCallback()) {

    inner class ListLocationsViewHolder(private val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(location: Location) {
            binding.nome.text = location.name
            binding.root.setOnClickListener { onClick(location) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListLocationsViewHolder {
        val binding =
            ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListLocationsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListLocationsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<Location>() {
        override fun areItemsTheSame(oldItem: Location, newItem: Location) =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: Location, newItem: Location) =
            oldItem == newItem
    }
}
