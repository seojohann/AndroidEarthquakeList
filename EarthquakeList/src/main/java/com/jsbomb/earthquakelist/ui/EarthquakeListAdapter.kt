package com.jsbomb.earthquakelist.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jsbomb.earthquakelist.data.EarthquakeData
import com.jsbomb.earthquakelist.databinding.EarthquakeListItemBinding

class EarthquakeListAdapter(private val onEarthquakeClickListener: OnEarthquakeClickListener) :
    ListAdapter<EarthquakeData, EarthquakeListAdapter.EarthquakeItemViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EarthquakeItemViewHolder {
        val binding =
            EarthquakeListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return EarthquakeItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EarthquakeItemViewHolder, position: Int) {
        holder.bind(getItem(position), onEarthquakeClickListener)
    }

    class EarthquakeItemViewHolder(private val binding: EarthquakeListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(earthquakeItem: EarthquakeData, onEarthquakeClickListener: OnEarthquakeClickListener) {
            binding.earthquake = earthquakeItem
            binding.root.setOnClickListener {
                onEarthquakeClickListener(earthquakeItem)
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<EarthquakeData>() {
            override fun areItemsTheSame(oldItem: EarthquakeData, newItem: EarthquakeData)
                    : Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: EarthquakeData, newItem: EarthquakeData)
                    : Boolean {
                return oldItem.properties?.time == newItem.properties?.time
                        && oldItem.properties?.place == newItem.properties?.place
                        && oldItem.properties?.mag == newItem.properties?.mag
            }

        }
    }
}

typealias OnEarthquakeClickListener = (EarthquakeData) -> Unit

data class EarthquakeItem(
    val time: Long,
    val place: String,
    val magnitude: Long
)