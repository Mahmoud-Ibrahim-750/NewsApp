package com.mis.route.newsapp.ui.home.fragments.news

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mis.route.newsapp.webservices.ApiManager
import com.mis.route.newsapp.webservices.models.articles.Article
import com.mis.route.newsapp.webservices.models.articles.ArticlesResponse
import com.mis.route.newsapp.webservices.models.sources.Source
import com.mis.route.newsapp.webservices.models.sources.SourcesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel : ViewModel() {

    var sourcesList: MutableLiveData<List<Source?>?> = MutableLiveData()
    var articlesList: MutableLiveData<List<Article?>?> = MutableLiveData()
    var dialogMessage: MutableLiveData<DialogMessage> = MutableLiveData()

    fun getSources(category: String) {
        // execute() is used ONLY when on a background thread
//        val response = ApiManager.getApi().getSources().execute()

        ApiManager.getApi().getSources(category = category).enqueue(
            object : Callback<SourcesResponse> {
                override fun onResponse(
                    call: Call<SourcesResponse>,
                    response: Response<SourcesResponse>
                ) {
                    if (response.isSuccessful) {
                        sourcesList.postValue(response.body()?.sources)
                    } else {
                        dialogMessage.postValue(
                            DialogMessage(
                                "Something Went Wrong (${response.code()})", response.message(),
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
                    }
                }

                override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
                    dialogMessage.postValue(
                        DialogMessage(
                            "Something Went Wrong",
                            t.localizedMessage,
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
        )
    }

    fun getArticles(source: String) {
        // used ONLY when on a background thread
//        val response = ApiManager.getApi().getSources().execute()
        ApiManager.getApi().getArticles(sources = source).enqueue(
            object : Callback<ArticlesResponse> {
                override fun onResponse(
                    call: Call<ArticlesResponse>,
                    response: Response<ArticlesResponse>
                ) {
                    if (response.isSuccessful) {
                        articlesList.postValue(response.body()?.articles)
                    } else {
                        dialogMessage.postValue(
                            DialogMessage(
                                "Something Went Wrong (${response.code()})",
                                response.message(),
                                "Try Again",
                                { dialogInterface, _ ->
                                    dialogInterface.dismiss()
                                    getArticles(source)
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

                override fun onFailure(call: Call<ArticlesResponse>, t: Throwable) {
                    dialogMessage.postValue(
                        DialogMessage(
                            "Something Went Wrong",
                            t.localizedMessage,
                            "Try Again",
                            { dialogInterface, _ ->
                                dialogInterface.dismiss()
                                getArticles(source)
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
        )
    }

}