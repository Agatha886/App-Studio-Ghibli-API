package br.com.agatha.monfredini.app_studio_ghibli_api.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.agatha.monfredini.app_studio_ghibli_api.databinding.ItemCharacterBinding
import br.com.agatha.monfredini.studio_ghibli_api.model.GhibliCharacter

class ListCharactersAdapter(
    private val onClick: (GhibliCharacter) -> Unit
) :
    ListAdapter<GhibliCharacter, ListCharactersAdapter.ListCharactersViewHolder>(DiffCallback()) {

    inner class ListCharactersViewHolder(private val binding: ItemCharacterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(character: GhibliCharacter) {
            binding.nome.text = character.name
            binding.root.setOnClickListener { onClick(character) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListCharactersViewHolder {
        val binding = ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListCharactersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListCharactersViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<GhibliCharacter>() {
        override fun areItemsTheSame(oldItem: GhibliCharacter, newItem: GhibliCharacter) = oldItem.name == newItem.name
        override fun areContentsTheSame(oldItem: GhibliCharacter, newItem: GhibliCharacter) = oldItem == newItem
    }
}
