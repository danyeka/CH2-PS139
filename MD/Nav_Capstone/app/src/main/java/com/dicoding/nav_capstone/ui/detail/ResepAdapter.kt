package com.dicoding.nav_capstone.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.nav_capstone.R
import com.dicoding.nav_capstone.data.remote.response.ArticleListItem
import com.dicoding.nav_capstone.data.remote.response.ResepTerkaitItem
import com.dicoding.nav_capstone.databinding.ActivityDetailBinding
import com.dicoding.nav_capstone.databinding.ActivityResepBinding
import com.dicoding.nav_capstone.databinding.ItemArtikelBinding
import com.dicoding.nav_capstone.databinding.ItemResepBinding

class ResepAdapter(
    private val itemClicklistener: OnItemClickListener
): ListAdapter<ResepTerkaitItem, ResepAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemResepBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val resep = getItem(position)
        holder.bind(resep)
    }

    inner class MyViewHolder(val binding: ItemResepBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(resep: ResepTerkaitItem) {
            binding.apply {
                tvResep.text = "${resep.namaResep}"
                tvRempahTerkait.text = "${resep.relatedKeyword}"
                Glide.with(itemView)
                    .load(resep.image)
                    .placeholder(R.drawable.load_gambar) // placeholder gambar sementara
                    .error(R.drawable.error_gambar)
                    .into(ivResep)
                root.setOnClickListener {
                    resep.idResep.let { it1 -> itemClicklistener.onStoryClicked(it1) }
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onStoryClicked(id: String)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ResepTerkaitItem>() {
            override fun areItemsTheSame(
                oldItem: ResepTerkaitItem,
                newItem: ResepTerkaitItem
            ): Boolean {
                return oldItem.namaResep == newItem.namaResep
            }

            override fun areContentsTheSame(
                oldItem: ResepTerkaitItem,
                newItem: ResepTerkaitItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}