package com.advanced.advanceddragonball.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.advanced.advanceddragonball.R
import com.advanced.advanceddragonball.databinding.ItemListBinding
import com.advanced.advanceddragonball.domain.Hero

class HeroListAdapter(private val clickListener: (Hero) -> (Unit)) :
    ListAdapter<Hero, HeroListAdapter.HeroViewHolder>(HeroItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        val binding = ItemListBinding.bind(view)

        return HeroViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HeroViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class HeroViewHolder(private val binding: ItemListBinding): RecyclerView.ViewHolder(binding.root) {

        private lateinit var hero: Hero
        init {
            binding.root.setOnClickListener {
                clickListener(hero)
            }
        }

        fun bind(hero: Hero) {
            this.hero = hero

            with(binding){
                heroName.text = hero.name
                heroImage.load(hero.photo)
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