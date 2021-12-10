package com.example.android.marsphotos.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.marsphotos.databinding.GridViewItemBinding
import com.example.android.marsphotos.network.MarsPhoto


class PhotoGridAdapter : ListAdapter<MarsPhoto, PhotoGridAdapter
.MarsPhotoViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<MarsPhoto>() {
        override fun areItemsTheSame(
            oldItem: MarsPhoto,
            newItem: MarsPhoto
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: MarsPhoto,
            newItem: MarsPhoto
        ): Boolean {
            return oldItem.imgSrcUrl == newItem.imgSrcUrl
        }

    }

    class MarsPhotoViewHolder(
        private var binding:
        GridViewItemBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(marsPhoto: MarsPhoto) {
            binding.photo = marsPhoto
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MarsPhotoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MarsPhotoViewHolder(GridViewItemBinding.inflate(inflater))
    }

    override fun onBindViewHolder(
        holder: MarsPhotoViewHolder, position: Int
    ) {
        val marsPhoto = getItem(position)
        holder.bind(marsPhoto)
    }
}