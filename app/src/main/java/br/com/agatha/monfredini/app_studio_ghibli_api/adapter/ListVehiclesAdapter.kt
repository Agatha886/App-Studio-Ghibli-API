package br.com.agatha.monfredini.app_studio_ghibli_api.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.agatha.monfredini.app_studio_ghibli_api.databinding.ItemCharacterBinding
import br.com.agatha.monfredini.studio_ghibli_api.model.Vehicle

class ListVehiclesAdapter(
    private val onClick: (Vehicle) -> Unit
) :
    ListAdapter<Vehicle, ListVehiclesAdapter.ListVehiclesViewHolder>(DiffCallback()) {

    inner class ListVehiclesViewHolder(private val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(vehicle: Vehicle) {
            binding.nome.text = vehicle.name
            binding.root.setOnClickListener { onClick(vehicle) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListVehiclesViewHolder {
        val binding =
            ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListVehiclesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListVehiclesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<Vehicle>() {
        override fun areItemsTheSame(oldItem: Vehicle, newItem: Vehicle) =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: Vehicle, newItem: Vehicle) =
            oldItem == newItem
    }
}
