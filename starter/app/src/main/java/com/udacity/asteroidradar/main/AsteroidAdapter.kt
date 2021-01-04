package com.udacity.asteroidradar.main

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.data.Asteroid


typealias ItemClickHandler = (Asteroid) -> Unit
class AsteroidAdapter(val itemClickListener: ItemClickHandler) : RecyclerView.Adapter<AsteroidViewHolder>(){

    private val diff = AsyncListDiffer(this, AsteroidDiffCallback())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        return AsteroidViewHolder.create(parent)
    }

    var items : List<Asteroid> = emptyList()
        set(value){
            field = value
            diff.submitList(value)
        }
    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        holder.bind(items[position],itemClickListener)
    }

    override fun getItemCount(): Int  = items.size

}