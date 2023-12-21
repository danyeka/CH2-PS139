package com.dicoding.nav_capstone.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.nav_capstone.data.remote.response.RecommendedRempahItem
import com.dicoding.nav_capstone.databinding.ItemRempahBinding

class RekomendasiAdapter(
    private val itemClicklistener: OnItemClickListener
) : ListAdapter<RecommendedRempahItem, RekomendasiAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemRempahBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val rekomendasiRempah = getItem(position)
        holder.bind(rekomendasiRempah)
    }

    inner class MyViewHolder(val binding: ItemRempahBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(rekomendasiRempah: RecommendedRempahItem) {
            binding.apply {
                tvRempah.text = "${rekomendasiRempah.namaRempah}"
                Glide.with(itemView)
                    .load(rekomendasiRempah.image)
                    .into(ivRempah)
                root.setOnClickListener {
                    rekomendasiRempah.idRempah.let { it1 ->
                        itemClicklistener.onStoryClicked(
                            it1.toString(),
                            "rempah"
                        )
                    }
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onStoryClicked(id: String, type: String)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RecommendedRempahItem>() {
            override fun areItemsTheSame(
                oldItem: RecommendedRempahItem,
                newItem: RecommendedRempahItem
            ): Boolean {
                return oldItem.idRempah == newItem.idRempah
            }

            override fun areContentsTheSame(
                oldItem: RecommendedRempahItem,
                newItem: RecommendedRempahItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}