package com.dicoding.nav_capstone.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.nav_capstone.data.remote.response.DictionaryRempahItem
import com.dicoding.nav_capstone.databinding.ItemListRempahBinding

class KamusAdapter(
    private val itemClicklistener: OnItemClickListener
) : ListAdapter<DictionaryRempahItem, KamusAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemListRempahBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val kamusRempah = getItem(position)
        holder.bind(kamusRempah)
    }

    inner class MyViewHolder(val binding: ItemListRempahBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(kamusRempah: DictionaryRempahItem) {
            binding.apply {
                tvNamaRempah.text = "${kamusRempah.namaRempah}"
                tvLatinRempah.text = "${kamusRempah.namaLatin}"
                tvDescRempah.text = "${kamusRempah.deskripsi}"
                Glide.with(itemView)
                    .load(kamusRempah.image)
                    .into(ivRempah)
                root.setOnClickListener {
                    kamusRempah.idRempah.let { it1 ->
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
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DictionaryRempahItem>() {
            override fun areItemsTheSame(
                oldItem: DictionaryRempahItem,
                newItem: DictionaryRempahItem
            ): Boolean {
                return oldItem.idRempah == newItem.idRempah
            }

            override fun areContentsTheSame(
                oldItem: DictionaryRempahItem,
                newItem: DictionaryRempahItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}