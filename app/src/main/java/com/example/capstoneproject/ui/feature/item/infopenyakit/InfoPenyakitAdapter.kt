package com.example.capstoneproject.ui.feature.item.infopenyakit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.capstoneproject.data.response.DataItem
import com.example.capstoneproject.databinding.ItemPenyakitBinding

class InfoPenyakitAdapter: RecyclerView.Adapter<InfoPenyakitAdapter.ListViewHolder>() {

    private val list = ArrayList<DataItem>()
    private var onItemClickCallback: OnItemClickCallback? = null
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
    fun setData(data: MutableList<DataItem>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemPenyakitBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = list[position]
        holder.bind(data)
    }

    inner class ListViewHolder(private val binding: ItemPenyakitBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(data: DataItem) {
                binding.root.setOnClickListener {
                    onItemClickCallback?.onItemClickCallBack(data)
                }
                binding.apply {
                    tvTitle.text = data.name
                    tvDescription.text = data.description
                    Glide.with(itemView.context)
                        .load(data.imageURL)
                        .into(ivPreviewImageInfo)
                }
            }
        }

    interface OnItemClickCallback {
        fun onItemClickCallBack(data: DataItem)
    }
}