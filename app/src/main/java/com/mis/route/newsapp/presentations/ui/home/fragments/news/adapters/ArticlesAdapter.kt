package com.mis.route.newsapp.presentations.ui.home.fragments.news.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mis.route.newsapp.data.data_sources.remote.news_api.models.articles.Article
import com.mis.route.newsapp.databinding.ItemArticleBinding

class ArticlesAdapter(private var articlesList: MutableList<Article?>) :
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

    override fun getItemCount() = articlesList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = articlesList[position] ?: return
        holder.bind(article)
        holder.binding.root.setOnClickListener {
            onArticleClickListener.onClick(articlesList[position], position)
        }

        if (position == articlesList.size - 1) onRequestNewPageListener?.onRequestNewPage()
    }

    fun addArticles(articlesList: List<Article?>) {
        this.articlesList.addAll(articlesList.toMutableList())
    }

    lateinit var onArticleClickListener: OnArticleClickListener

    fun interface OnArticleClickListener {
        fun onClick(article: Article?, position: Int)
    }

    private var onRequestNewPageListener: OnRequestNewPageListener? = null

    fun setOnRequestNewPageListener(listener: OnRequestNewPageListener) {
        onRequestNewPageListener = listener
    }

    fun interface OnRequestNewPageListener {
        fun onRequestNewPage()
    }
}