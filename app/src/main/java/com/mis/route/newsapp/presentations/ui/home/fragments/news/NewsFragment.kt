package com.mis.route.newsapp.presentations.ui.home.fragments.news

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mis.route.newsapp.Constants
import com.mis.route.newsapp.R
import com.mis.route.newsapp.data.data_sources.local.models.sources.MiniSource
import com.mis.route.newsapp.databinding.FragmentNewsBinding
import com.mis.route.newsapp.presentations.ui.home.fragments.article_details.ArticleDetailsFragment
import com.mis.route.newsapp.presentations.ui.home.fragments.news.adapters.ArticlesAdapter
import com.mis.route.newsapp.presentations.ui.home.fragments.news.adapters.SourcesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment(private val category: String) : Fragment() {

    private lateinit var binding: FragmentNewsBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var currentSelectedSource: MiniSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[NewsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setObservers()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setObservers() {
        viewModel.sourcesList.observe(viewLifecycleOwner) { sourcesList ->
            if (sourcesList?.size == 0) {
                binding.noSourcesTextview.isVisible = true
                binding.sourcesProgressBar.isVisible = false
                binding.sourcesRecycler.isVisible = false
                return@observe
            }
            // sources progress
            binding.sourcesProgressBar.visibility = View.GONE
            binding.noSourcesTextview.isVisible = false
            binding.sourcesRecycler.visibility = View.VISIBLE
            sourcesAdapter.sourcesList = sourcesList
            sourcesAdapter.notifyItemRangeInserted(0, sourcesList!!.size)
            // articles progress
            binding.articlesProgressBar.isVisible = true
            binding.articlesRecycler.isVisible = false
            // load first source by default
            sourcesList[0]?.let {
                currentSelectedSource = it.toMiniSource()
                viewModel.getArticles(currentSelectedSource)
            }
        }

        viewModel.articlesList.observe(viewLifecycleOwner) {
            if (it?.size == 0) {
                binding.articlesProgressBar.visibility = View.GONE
                binding.articlesRecycler.visibility = View.GONE
                binding.noArticlesTextview.isVisible = true
                return@observe
            }
            binding.articlesProgressBar.visibility = View.GONE
            binding.articlesRecycler.visibility = View.VISIBLE
            articlesAdapter.articlesList = it
            articlesAdapter.notifyDataSetChanged()
        }

        viewModel.dialogMessage.observe(viewLifecycleOwner) {
            showDialog(it)
        }
    }

    private val sourcesAdapter = SourcesAdapter(null)
    private val articlesAdapter = ArticlesAdapter(null)

    private fun initViews() {
        // sources recycler view
        viewModel.getSources(category)
        sourcesAdapter.onSourceClickListener =
            SourcesAdapter.OnSourceClickListener { source, oldPosition, newPosition ->
                sourcesAdapter.notifyItemChanged(oldPosition) // to remove highlight
                sourcesAdapter.notifyItemChanged(newPosition) // to add highlight
                binding.articlesProgressBar.isVisible = true
                binding.articlesRecycler.isVisible = false
                currentSelectedSource = source.toMiniSource()
                viewModel.getArticles(currentSelectedSource)
            }
        binding.sourcesRecycler.adapter = sourcesAdapter

        // articles recycler view
        articlesAdapter.onArticleClickListener =
            ArticlesAdapter.OnArticleClickListener { article, _ ->
                val args = Bundle()
                args.putParcelable(Constants.PASSED_ARTICLE_KEY, article)
                parentFragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_container, ArticleDetailsFragment::class.java, args)
                    .addToBackStack("ArticleDetailsFragment")
                    .commit()
            }
        binding.articlesRecycler.adapter = articlesAdapter
    }

    fun performSearch(query: String) {
        viewModel.getArticles(currentSelectedSource, query)
    }

    private fun Fragment.showDialog(dialogMessage: DialogMessage) {
        AlertDialog.Builder(context)
            .setTitle(dialogMessage.title)
            .setMessage(dialogMessage.message)
            .setPositiveButton(dialogMessage.posText, dialogMessage.posAction)
            .setNegativeButton(dialogMessage.negText, dialogMessage.negAction)
            .setCancelable(dialogMessage.cancelable)
            .show()
    }
}
