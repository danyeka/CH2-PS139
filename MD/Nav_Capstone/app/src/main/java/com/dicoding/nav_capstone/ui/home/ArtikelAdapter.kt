package com.dicoding.nav_capstone.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.nav_capstone.data.remote.response.ArticleListItem
import com.dicoding.nav_capstone.databinding.ItemArtikelBinding

class ArtikelAdapter(
    private val itemClicklistener: OnItemClickListener
) : ListAdapter<ArticleListItem, ArtikelAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemArtikelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val artikel = getItem(position)
        holder.bind(artikel)
    }

    inner class MyViewHolder(val binding: ItemArtikelBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(artikel: ArticleListItem) {
            binding.apply {
                tvJudulArtikel.text = "${artikel.judulArtikel}"
                Glide.with(itemView)
                    .load(artikel.image)
                    .into(ivArtikel)
                root.setOnClickListener {
                    artikel.idArtikel.let { it1 ->
                        itemClicklistener.onStoryClicked(
                            it1.toString(),
                            "artikel"
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
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ArticleListItem>() {
            override fun areItemsTheSame(
                oldItem: ArticleListItem,
                newItem: ArticleListItem
            ): Boolean {
                return oldItem.idArtikel == newItem.idArtikel
            }

            override fun areContentsTheSame(
                oldItem: ArticleListItem,
                newItem: ArticleListItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}