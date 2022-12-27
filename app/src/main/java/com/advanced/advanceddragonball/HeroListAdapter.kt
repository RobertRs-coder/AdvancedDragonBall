package com.advanced.advanceddragonball

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.advanced.advanceddragonball.databinding.ItemListBinding

class HeroListAdapter: ListAdapter<Hero, HeroListAdapter.HeroViewHolder>(HeroItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        val binding = ItemListBinding.bind(view)

        return HeroViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HeroViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class HeroViewHolder(private val binding: ItemListBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(hero: Hero) {
            with(binding){
                heroName.text = hero.name
            }
        }
    }

    class HeroItemDiffCallback: DiffUtil.ItemCallback<Hero>() {
        override fun areItemsTheSame(oldItem: Hero, newItem: Hero): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Hero, newItem: Hero): Boolean {
            // You can compare because the items are data classes
            return oldItem == newItem
        }

    }
}