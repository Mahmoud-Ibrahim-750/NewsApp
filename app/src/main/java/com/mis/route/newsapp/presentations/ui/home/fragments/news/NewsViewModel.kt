package com.mis.route.newsapp.presentations.ui.home.fragments.news

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mis.route.newsapp.data.data_sources.local.models.sources.MiniSource
import com.mis.route.newsapp.data.data_sources.remote.news_api.models.articles.Article
import com.mis.route.newsapp.data.data_sources.remote.news_api.models.sources.Source
import com.mis.route.newsapp.domain.repositories.ArticleRepository
import com.mis.route.newsapp.domain.repositories.ArticlesSourceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val articlesSourceRepository: ArticlesSourceRepository,
    private val articleRepository: ArticleRepository
) : ViewModel() {

    var sourcesList: MutableLiveData<List<Source?>?> = MutableLiveData()
    var articlesList: MutableLiveData<List<Article?>?> = MutableLiveData()
    var dialogMessage: MutableLiveData<DialogMessage> = MutableLiveData()
    private var currentArticlesPage = 1

    fun getSources(category: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                sourcesList.postValue(articlesSourceRepository.getSources(category))
            } catch (e: HttpException) {
                dialogMessage.postValue(
                    createErrorDialogMessage(
                        code = e.code(),
                        exception = e,
                        retryAction = { getSources(category) })
                )
            } catch (e: Exception) {
                dialogMessage.postValue(
                    createErrorDialogMessage(
                        exception = e,
                        retryAction = { getSources(category) })
                )
            }
        }
    }

    fun requestNextArticlesPage(source: MiniSource, query: String? = null) {
        getArticles(source, query, ++currentArticlesPage)
    }

    fun getArticles(source: MiniSource, query: String? = null, page: Int = currentArticlesPage) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                articlesList.postValue(articleRepository.getArticle(source, query, page))
            } catch (e: HttpException) {
                dialogMessage.postValue(
                    createErrorDialogMessage(
                        code = e.code(),
                        exception = e,
                        retryAction = { getArticles(source) })
                )
            } catch (e: Exception) {
                dialogMessage.postValue(
                    createErrorDialogMessage(
                        exception = e,
                        retryAction = { getArticles(source) })
                )
            }
        }
    }

    private fun createErrorDialogMessage(
        code: Int? = null,
        exception: Exception,
        retryAction: (() -> Unit)
    ): DialogMessage {
        val titleBoundCode = if (code != null) ", code $code" else ""
        return DialogMessage(
            "Something Went Wrong$titleBoundCode",
            exception.message,
            "Try Again",
            { dialogInterface, _ ->
                dialogInterface.dismiss()
                retryAction.invoke()
            },
            "Cancel",
            { dialogInterface, _ ->
                dialogInterface.dismiss()
            },
            true
        )
    }
}