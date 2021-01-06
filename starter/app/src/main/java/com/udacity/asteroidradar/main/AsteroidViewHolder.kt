package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.data.Asteroid
import com.udacity.asteroidradar.databinding.LayoutAsteroidItemBinding

/**
 *
 * @author Narendra Darla(R)
 */
class AsteroidViewHolder(val binding: LayoutAsteroidItemBinding): RecyclerView.ViewHolder(binding.root) {
    companion object{
        fun create(parent: ViewGroup):AsteroidViewHolder{
            val inflater = LayoutInflater.from(parent.context)
            val binding = LayoutAsteroidItemBinding.inflate(inflater,parent, false)
            return AsteroidViewHolder(binding)
        }
    }
    fun bind(asteroid: Asteroid, itemClickHandler: ItemClickHandler) {
        println("Asteroid ${asteroid.id}")
        binding.asteroid = asteroid
        binding.root.setOnClickListener{ itemClickHandler(asteroid)}
        binding.executePendingBindings()
    }
}
