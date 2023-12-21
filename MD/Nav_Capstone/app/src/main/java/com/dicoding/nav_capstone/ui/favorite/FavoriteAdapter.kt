package com.dicoding.nav_capstone.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.nav_capstone.data.local.database.FavoriteRempah
import com.dicoding.nav_capstone.databinding.ItemFavRempahBinding

class FavoriteAdapter(
    private val itemClicklistener: OnItemClickListener
) : ListAdapter<FavoriteRempah, FavoriteAdapter.MyViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemFavRempahBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val favRempah = getItem(position)
        holder.bind(favRempah)
    }

    inner class MyViewHolder(val binding: ItemFavRempahBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favRempah: FavoriteRempah){
            binding.tvNamaRempah.text = "${favRempah.nama}"
            binding.tvLatinRempah.text = "${favRempah.latin}"
//            binding.tvId.text = "ID ${user.id}"
            Glide.with(itemView)
                .load(favRempah.image)
                .into(binding.ivRempah)
            binding.root.setOnClickListener {
                favRempah.idRempah.let { it1 -> itemClicklistener.onStoryClicked(it1.toString()) }
            }
        }
    }

    interface OnItemClickListener {
        fun onStoryClicked(id: String)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FavoriteRempah>() {
            override fun areItemsTheSame(oldItem: FavoriteRempah, newItem: FavoriteRempah): Boolean {
                return oldItem.idRempah == newItem.idRempah
            }

            override fun areContentsTheSame(oldItem: FavoriteRempah, newItem: FavoriteRempah): Boolean {
                return oldItem == newItem
            }

        }
    }
}