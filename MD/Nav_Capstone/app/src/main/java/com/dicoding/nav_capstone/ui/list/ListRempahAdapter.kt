package com.dicoding.nav_capstone.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.nav_capstone.data.remote.response.ListRempahItem
import com.dicoding.nav_capstone.databinding.ItemListRempahBinding

class ListRempahAdapter(
    private val itemClicklistener: OnItemClickListener
) : ListAdapter<ListRempahItem, ListRempahAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemListRempahBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val rempah = getItem(position)
        holder.bind(rempah)
    }

    inner class MyViewHolder(val binding: ItemListRempahBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(rempah: ListRempahItem) {
            binding.apply {
                tvNamaRempah.text = "${rempah.namaRempah}"
                tvLatinRempah.text = "${rempah.namaLatin}"
                tvDescRempah.text = "${rempah.deskripsi}"
                Glide.with(itemView)
                    .load(rempah.image)
                    .into(ivRempah)
                root.setOnClickListener {
                    rempah.idRempah.let { it1 -> itemClicklistener.onStoryClicked(it1.toString()) }
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onStoryClicked(id: String)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListRempahItem>() {
            override fun areItemsTheSame(
                oldItem: ListRempahItem,
                newItem: ListRempahItem
            ): Boolean {
                return oldItem.idRempah == newItem.idRempah
            }

            override fun areContentsTheSame(
                oldItem: ListRempahItem,
                newItem: ListRempahItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}