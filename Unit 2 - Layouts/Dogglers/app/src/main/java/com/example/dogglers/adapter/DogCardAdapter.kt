/*
* Copyright (C) 2021 The Android Open Source Project.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.example.dogglers.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dogglers.R
import com.example.dogglers.const.Layout
import com.example.dogglers.model.Dog

/**
 * Adapter to inflate the appropriate list item layout and populate the view with information
 * from the appropriate data source
 */
class DogCardAdapter(
    private val context: Context,
    private val layout: Int,
    private val dogs: List<Dog>
) : RecyclerView.Adapter<DogCardAdapter.DogCardViewHolder>() {

    // TODO: Initialize the data using the List found in data/DataSource

    /**
     * Initialize view elements
     */
    class DogCardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView = view.findViewById<ImageView>(R.id.image)
        val nameView = view.findViewById<TextView>(R.id.name)
        val ageView = view.findViewById<TextView>(R.id.age)
        val hobbiesView = view.findViewById<TextView>(R.id.hobbies)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogCardViewHolder {
        val cardLayout = when (layout) {
            Layout.GRID -> R.layout.grid_list_item
            else -> R.layout.vertical_horizontal_list_item
        }
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(cardLayout, parent, false)
        return DogCardViewHolder(adapterLayout)
    }

    override fun getItemCount() = dogs.size

    override fun onBindViewHolder(holder: DogCardViewHolder, position: Int) {
        val resources = context.resources
        val dog = dogs[position]
        holder.imageView.setImageResource(dog.imageResourceId)
        holder.nameView.text = dog.name
        holder.ageView.text = resources.getString(R.string.dog_age, dog.age)
        holder.hobbiesView.text = resources.getString(R.string.dog_hobbies, dog.hobbies)
    }
}
