package com.ev.dpadnavsystem.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ev.dpadnavsystem.data.model.ChargersModel
import com.ev.dpadnavsystem.databinding.ItemFavoritesBinding

class ChargersAdapter(
    private val data: MutableList<ChargersModel>,
) : RecyclerView.Adapter<ChargersAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemFavoritesBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position], position)
    }

    inner class ViewHolder(private val binding: ItemFavoritesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(chargersModel: ChargersModel, position: Int) {
            binding.tvItemFavorite.text = chargersModel.name
            binding.isEnabled = chargersModel.isSelected
            binding.tvItemFavorite.setOnClickListener {
                selectItem(position)
            }
        }

        private fun selectItem(position: Int) {
            // Set the clicked item as selected and all others as unselected
            for (i in data.indices) {
                data[i].isSelected = i == position
            }
            // Notify the adapter that the data has changed to refresh the UI
            notifyDataSetChanged()
        }
    }
}