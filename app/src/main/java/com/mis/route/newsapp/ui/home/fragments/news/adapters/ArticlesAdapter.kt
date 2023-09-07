package com.mis.route.newsapp.ui.home.fragments.news.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mis.route.newsapp.databinding.ItemArticleBinding
import com.mis.route.newsapp.webservices.models.articles.Article

class ArticlesAdapter(var articlesList: List<Article?>?) :
    RecyclerView.Adapter<ArticlesAdapter.ViewHolder>() {
    private lateinit var binding: ItemArticleBinding

    class ViewHolder(val binding: ItemArticleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article?) {
            article?.let {
                binding.article = article
                binding.articleSourceName.text = it.source?.name
                binding.articleHeadline.text = it.title
                binding.articlePublishDate.text = it.publishedAt
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = articlesList?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(articlesList!![position])
        holder.binding.root.setOnClickListener {
            onArticleClickListener.onClick(articlesList!![position], position)
        }
    }

    lateinit var onArticleClickListener: OnArticleClickListener

    fun interface OnArticleClickListener {
        fun onClick(article: Article?, position: Int)
    }
}