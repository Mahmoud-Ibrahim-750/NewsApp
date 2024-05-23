package com.mis.route.newsapp.ui.home.fragments.news

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mis.route.newsapp.webservices.ApiManager
import com.mis.route.newsapp.webservices.models.articles.Article
import com.mis.route.newsapp.webservices.models.sources.Source
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class NewsViewModel : ViewModel() {

    var sourcesList: MutableLiveData<List<Source?>?> = MutableLiveData()
    var articlesList: MutableLiveData<List<Article?>?> = MutableLiveData()
    var dialogMessage: MutableLiveData<DialogMessage> = MutableLiveData()

    fun getSources(category: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = ApiManager.getApi().getSources(category = category)
                sourcesList.postValue(response.sources)
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

    fun getArticles(source: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = ApiManager.getApi().getArticles(sources = source)
                articlesList.postValue(response.articles)
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