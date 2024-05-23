package com.mis.route.newsapp.presentations.ui.home.fragments.article_details

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.mis.route.newsapp.Constants
import com.mis.route.newsapp.data.data_sources.remote.news_api.models.articles.Article
import com.mis.route.newsapp.databinding.FragmentArticleDetailsBinding

class ArticleDetailsFragment : Fragment() {

    private lateinit var binding: FragmentArticleDetailsBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArticleDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindArticle(getArticle())
    }

    private fun getArticle(): Article {
        val args = arguments ?: return Article()
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            args.getParcelable(Constants.PASSED_ARTICLE_KEY, Article::class.java) ?: Article()
        } else {
            @Suppress("DEPRECATION")
            args.getParcelable(Constants.PASSED_ARTICLE_KEY) ?: Article()
        }
    }

    private fun bindArticle(article: Article) {
        binding.apply {
            Glide.with(this@ArticleDetailsFragment)
                .load(article.urlToImage)
                .into(articleImg)
            sourceNameTxt.text = article.source?.name
            articleTitleTxt.text = article.title
            dateTxt.text = article.publishedAt
            articleDescriptionTxt.text = article.description
            fullArticleLinkTxt.setOnClickListener {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
                startActivity(browserIntent)
            }
        }
    }
}
