package com.mis.route.newsapp.presentations.ui.home.fragments.news.adapters

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mis.route.newsapp.R
import com.mis.route.newsapp.data.data_sources.remote.news_api.models.sources.Source
import com.mis.route.newsapp.databinding.ItemSourceBinding

class SourcesAdapter(var sourcesList: List<Source?>?) :
    RecyclerView.Adapter<SourcesAdapter.ViewHolder>() {
    private lateinit var binding: ItemSourceBinding

    class ViewHolder(val binding: ItemSourceBinding) : RecyclerView.ViewHolder(binding.root) {
        fun highlightSourceWhen(isSelected: Boolean) {
            val itemBackground: Int
            val textColor: Int
            if (isSelected) {
                itemBackground = ContextCompat.getColor(binding.root.context, R.color.green)
                textColor = ContextCompat.getColor(binding.root.context, R.color.white)
            } else {
                itemBackground = ContextCompat.getColor(binding.root.context, R.color.transparent)
                textColor = ContextCompat.getColor(binding.root.context, R.color.green)
            }
            binding.root.backgroundTintList = ColorStateList.valueOf(itemBackground)
            binding.sourceName.setTextColor(textColor)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemSourceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = sourcesList?.size ?: 0

    var selectedSourcePosition: Int = 0 // first selected by default
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val actualPosition = holder.adapterPosition
        holder.highlightSourceWhen(selectedSourcePosition == position)
        val source = sourcesList?.get(position) ?: return
        holder.binding.source = source

        holder.binding.root.setOnClickListener {
            onSourceClickListener.onClick(source, selectedSourcePosition, position)
            selectedSourcePosition = position
        }
    }

    lateinit var onSourceClickListener: OnSourceClickListener

    fun interface OnSourceClickListener {
        fun onClick(source: Source, oldPosition: Int, newPosition: Int)
    }
}