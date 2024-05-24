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

    fun getSources(category: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                sourcesList.postValue(articlesSourceRepository.getSources(category))
            } catch (e: HttpException) {
                dialogMessage.postValue(
                    DialogMessage(
                        "Something Went Wrong (${e.code()})",
                        e.message,
                        "Try Again", { dialogInterface, _ ->
                            dialogInterface.dismiss()
                            getSources(category)
                        },
                        "Cancel", { dialogInterface, _ ->
                            dialogInterface.dismiss()
                        },
                        true
                    )
                )
            } catch (e: Exception) {
                dialogMessage.postValue(
                    DialogMessage(
                        "Something Went Wrong",
                        e.localizedMessage,
                        "Try Again",
                        { dialogInterface, _ ->
                            dialogInterface.dismiss()
                            getSources(category)
                        },
                        "Cancel",
                        { dialogInterface, _ ->
                            dialogInterface.dismiss()
                        },
                        true
                    )
                )
            }
        }
    }

    fun getArticles(source: MiniSource, query: String? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                articlesList.postValue(articleRepository.getArticle(source, query))
            } catch (e: HttpException) {
                dialogMessage.postValue(
                    DialogMessage(
                        "Something Went Wrong (${e.code()})",
                        e.message(),
                        "Try Again",
                        { dialogInterface, _ ->
                            dialogInterface.dismiss()
                            viewModelScope.launch { getArticles(source) }
                        },
                        "Cancel",
                        { dialogInterface, _ ->
                            dialogInterface.dismiss()
                        },
                        true
                    )
                )
            } catch (e: Exception) {
                dialogMessage.postValue(
                    DialogMessage(
                        "Something Went Wrong",
                        e.localizedMessage,
                        "Try Again",
                        { dialogInterface, _ ->
                            dialogInterface.dismiss()
                            viewModelScope.launch { getArticles(source) }
                        },
                        "Cancel",
                        { dialogInterface, _ ->
                            dialogInterface.dismiss()
                        },
                        true
                    )
                )
            }
        }
    }
}