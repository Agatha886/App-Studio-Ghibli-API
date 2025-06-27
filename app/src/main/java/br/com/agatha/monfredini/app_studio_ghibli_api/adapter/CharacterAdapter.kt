package br.com.agatha.monfredini.app_studio_ghibli_api.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.agatha.monfredini.app_studio_ghibli_api.databinding.ItemCharacterBinding
import br.com.agatha.monfredini.studio_ghibli_api.model.GhibliCharacter

class CharacterAdapter :
    ListAdapter<GhibliCharacter, CharacterAdapter.PersonagemViewHolder>(DiffCallback()) {

    inner class PersonagemViewHolder(private val binding: ItemCharacterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(personagem: GhibliCharacter) {
            binding.nome.text = personagem.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonagemViewHolder {
        val binding = ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PersonagemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PersonagemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<GhibliCharacter>() {
        override fun areItemsTheSame(oldItem: GhibliCharacter, newItem: GhibliCharacter) = oldItem.name == newItem.name
        override fun areContentsTheSame(oldItem: GhibliCharacter, newItem: GhibliCharacter) = oldItem == newItem
    }
}
