package com.example.dogapp.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dogapp.databinding.ItemDogDetailsBinding
import com.example.dogapp.utils.loadImageUrl

class DogListImagesAdapter: RecyclerView.Adapter<DogListImagesAdapter.ViewHolder>() {
    private val itemList: ArrayList<String> = arrayListOf()

    fun submitList(list: List<String>){
        itemList.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(
        val binding: ItemDogDetailsBinding
    ) :
        RecyclerView.ViewHolder(binding.root){

        fun bind(item: String)= with(binding) {
            binding.imgDogPicture.loadImageUrl(item)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemDogDetailsBinding = ItemDogDetailsBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position])
    }
}